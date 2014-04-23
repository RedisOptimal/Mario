/**
 * Copyright 2014 Renren.com Licensed under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package com.renren.Wario.plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.zookeeper.AsyncCallback.ChildrenCallback;
import org.apache.zookeeper.AsyncCallback.DataCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.data.Stat;

import com.renren.Wario.db.MySQLHelper;

public class ObserverPlugin extends IPlugin {

    private static final Logger logger =
            LogManager.getLogger(ObserverPlugin.class.getName());

    private class PathAndStat {

        private final String path;
        private final Stat stat;
        private final byte[] data;

        public PathAndStat(String path, byte[] data, Stat stat){
            this.path = path;
            this.data = data;
            this.stat = stat;
        }

        /**
         * @return the path
         */
        public String getPath() {
            return path;
        }

        /**
         * @return the data
         */
        public byte[] getData() {
            return data;
        }

        /**
         * @return the stat
         */
        public Stat getStat() {
            return stat;
        }
    }

    private final BlockingQueue<PathAndStat> saveQueue =
            new LinkedBlockingQueue<PathAndStat>();

    private final AtomicInteger inQueueProcess = new AtomicInteger(1);
    private final AtomicInteger inTrackProcess = new AtomicInteger(1);
    private final AtomicInteger inDBWriterProcess = new AtomicInteger(0);
    private final AtomicInteger inQueue = new AtomicInteger(1);
    private final AtomicInteger inTrack = new AtomicInteger(0);
    private final Long startTime = System.currentTimeMillis();
    private final MySQLHelper helper = new MySQLHelper();
    private final PreparedStatement updatePs;
    private final PreparedStatement insertPs;

    public ObserverPlugin() throws SQLException, ClassNotFoundException{
        helper.open();
        updatePs =
                helper.getPreparedStatement("update mario_node_state set data = ? "
                                            + ", data_length = ? "
                                            + ", num_children = ? "
                                            + ", version = ? "
                                            + ", aversion = ? "
                                            + ", cversion = ? "
                                            + ", ctime = ? "
                                            + ", mtime = ? "
                                            + ", czxid = ? "
                                            + ", mzxid = ? "
                                            + ", pzxid = ? "
                                            + ", ephemeral_owner = ? "
                                            + ", state_version = ? "
                                            + ", state_time = now() "
                                            + "where zk_id = ?  and path = ? and mzxid = ? ");
        insertPs =
                helper.getPreparedStatement("insert into mario_node_state (zk_id, "
                                            + "path, data, data_length, num_children, version, aversion, "
                                            + "cversion, ctime, mtime, czxid, mzxid, pzxid, ephemeral_owner, "
                                            + "state_version, state_time) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())");
        logger.info("ObserverPlugin init successful.");
    }

    @Override
    public void run() {
        if (client == null) {
            return;
        }
        int stateVersion = getNextStateVersion();
        
        Stat stat = new Stat();
        byte[] data = null;
        try {
            data = client.getData("/", stat);
        } catch (KeeperException e) {
            logger.error("Exception when get root ", e);
            return;
        } catch (InterruptedException e) {
            logger.error("Thread interrupted", e);
        }
        PathAndStat root = new PathAndStat("/", data, stat);
        saveQueue.offer(root);
        client.getChildren("/", false, new ChildrenCallback() {

            @Override
            public void processResult(int rc, String path, Object ctx,
                List<String> children) {
                synchronized (inQueue) {
                    inQueueProcess.incrementAndGet();
                    inQueue.decrementAndGet();
                    Code cd = KeeperException.Code.get(rc);
                    switch (cd) {
                        case OK:
                            break;
                        default:
                            logger.warn("GetChildren on path " + path
                                        + " with " + cd.toString() + " event.");
                            return;
                    }
                    inTrack.incrementAndGet();
                    client.getData(path, false, new DataCallback() {

                        @Override
                        public void processResult(int rc, String path, Object ctx,
                            byte[] data, Stat stat) {
                            inTrack.decrementAndGet();
                            Code cd = Code.get(rc);
                            switch (cd) {
                                case OK:
                                    break;
                                default:
                                    logger.warn("GetData on path " + path
                                                + " with " + cd.toString() + " event.");
                                    return;
                            }
                            PathAndStat node = new PathAndStat(path, data, stat);
                            inTrackProcess.incrementAndGet();
                            saveQueue.offer(node);
                        }
                    }, null);
                    Iterator<String> iterator = children.iterator();
                    while (iterator.hasNext()) {
                        String child = iterator.next();
                        String son = (path.endsWith("/") ? path : path + "/") + child;
                        inQueue.incrementAndGet();
                        client.getChildren(son, false, this, null);
                    }
                }
            }
        }, null);

        logger.info("Init finished " + (System.currentTimeMillis() - startTime)
                    + " ms");

        while (true) {
            if (inQueue.intValue() == 0 && inTrack.intValue() == 0 && saveQueue.isEmpty()) {
                break;
            }
            PathAndStat node = null;
            try {
                node = saveQueue.poll(10, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                logger.warn("Thread interrupted.", e);
                return;
            }

            if (node == null) {
                continue;
            }
            inDBWriterProcess.incrementAndGet();
            try {
                writeToDB(node, stateVersion);
            } catch (SQLException e) {
                logger.error("Execute sql failed!", e);
            }
        }
        
        try {
            updatePs.close();
            insertPs.close();
            helper.close();
        } catch (SQLException e) {
            logger.error("MysqlHelper close failed! ", e);
        }
        
        logger.info("Run main thread "
                    + (System.currentTimeMillis() - startTime) + " ms");
        logger.info("INQUEUE : " + inQueueProcess.intValue() + "\nINTRACK : "
                    + inTrackProcess.intValue() + "\nINDBWRITER : "
                    + inDBWriterProcess.intValue());
    }

    private int getNextStateVersion() {
        int maxVersion = 0;
        if (clusterContext[0] == '#' && clusterContext[5] == '#') {
            for (int i = 1; i <= 4; ++i) {
                maxVersion = (maxVersion << 8) | (clusterContext[i] & 0xff);
            }
        } else {
            maxVersion = getMaxStateVersionFromDB();
        }
        int tmp = ++maxVersion;

        clusterContext[0] = '#';
        for (int i = 4; i > 0; --i) {
            clusterContext[i] = (byte) (tmp & 0xff);
            tmp >>= 8;
        }
        clusterContext[5] = '#';
        deleteExpiredData(maxVersion);
        return maxVersion;
    }

    private int getMaxStateVersionFromDB() {
        int maxVersion = 0;
        String sql =
                "select max(state_version) from mario_node_state where zk_id = "
                        + client.getZkId();
        try {
            ResultSet rs = helper.executeQuery(sql);
            while (rs.next()) {
                maxVersion = rs.getInt("max(state_version)");
            }
        } catch (SQLException e) {
            logger.error("MysqlHelper open failed or execute sql " + sql
                         + " failed! ", e);
        } 
        return maxVersion;
    }

    private void deleteExpiredData(int nextStateVersion) {
        MySQLHelper helper = new MySQLHelper();
        String sql =
                "delete from mario_node_state where zk_id = " + client.getZkId() + " and state_version < "
                        + (nextStateVersion - 12 * 24 * 3);
        try {
            helper.open();
            helper.execute(sql);
        } catch (ClassNotFoundException e) {
            logger.error("MysqlHelper open failed! ", e);
        } catch (SQLException e) {
            logger.error("MysqlHelper open failed or execute sql " + sql
                         + " failed!", e);
        } finally {
            try {
                helper.close();
            } catch (SQLException e) {
                logger.error("MysqlHelper close failed!", e);
            }
        }
    }

    private void writeToDB(PathAndStat node, int nextStatVersion)
        throws SQLException {
        updatePs.setBytes(1, node.getData());
        updatePs.setInt(2, node.getStat().getDataLength());
        updatePs.setInt(3, node.getStat().getNumChildren());
        updatePs.setInt(4, node.getStat().getVersion());
        updatePs.setInt(5, node.getStat().getAversion());
        updatePs.setInt(6, node.getStat().getCversion());
        updatePs.setLong(7, node.getStat().getCtime());
        updatePs.setLong(8, node.getStat().getMtime());
        updatePs.setLong(9, node.getStat().getCzxid());
        updatePs.setLong(10, node.getStat().getMzxid());
        updatePs.setLong(11, node.getStat().getPzxid());
        updatePs.setLong(12, node.getStat().getEphemeralOwner());
        updatePs.setInt(13, nextStatVersion);
        updatePs.setInt(14, client.getZkId());
        updatePs.setString(15, node.getPath());
        updatePs.setLong(16, node.getStat().getMzxid());
        if (updatePs.executeUpdate() == 0) {
            insertPs.setInt(1, client.getZkId());
            insertPs.setString(2, node.getPath());
            insertPs.setBytes(3, node.getData());
            insertPs.setInt(4, node.getStat().getDataLength());
            insertPs.setInt(5, node.getStat().getNumChildren());
            insertPs.setInt(6, node.getStat().getVersion());
            insertPs.setInt(7, node.getStat().getAversion());
            insertPs.setInt(8, node.getStat().getCversion());
            insertPs.setLong(9, node.getStat().getCtime());
            insertPs.setLong(10, node.getStat().getMtime());
            insertPs.setLong(11, node.getStat().getCzxid());
            insertPs.setLong(12, node.getStat().getMzxid());
            insertPs.setLong(13, node.getStat().getPzxid());
            insertPs.setLong(14, node.getStat().getEphemeralOwner());
            insertPs.setInt(15, nextStatVersion);
            insertPs.execute();
        }
    }
}
