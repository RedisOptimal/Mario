package com.renren.infra.xweb.util.generator.db;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.renren.infra.xweb.util.generator.config.ApplicationProperties;

/**
 * 数据库连接
 * 
 * @author yong.cao
 * 
 */
public class DBConnection {

    private static final Logger LOGGER = LoggerFactory.getLogger(DBConnection.class);

    private Connection conn = null;

    private String username;

    private String password;

    private String driver;

    private String url;

    private String error;

    public DBConnection() {
        driver = ApplicationProperties.getJdbcDriver();
        url = ApplicationProperties.getJdbcUrl();
        username = ApplicationProperties.getJdbcUsername();
        password = ApplicationProperties.getJdbcPassword();
        error = "";
        conn = null;
    }

    /**
     * 关闭
     * 
     * @param commit
     */
    public void close(boolean commit) {
        try {
            if (commit) {
                commit();
            } else {
                rollback();
            }

            if (conn != null) {
                conn.close();
            }
            conn = null;
        } catch (Exception e) {
            error = "Close connect Sqlerror to " + url + " " + e.toString();
            LOGGER.error(error);
            e.printStackTrace();
        }
    }

    /**
     * 回滚
     */
    public void rollback() {
        try {
            if (conn != null) {
                conn.rollback();
            }
        } catch (Exception e) {
            error = "Rollback Sqlerror to " + url + " " + e.toString();
            LOGGER.error(error);
            e.printStackTrace();
        }
    }

    /**
     * 提交
     */
    public void commit() {
        try {
            if (conn != null) {
                conn.commit();
            }
        } catch (Exception e) {
            error = "Commit Sqlerror to " + url + " " + e.toString();
            LOGGER.error(error);
            e.printStackTrace();
        }
    }

    /**
     * 创建数据源链接
     */
    public void connect() {
        try {
            if (conn != null) {
                close(true);
            }

            Class.forName(driver);

            if (StringUtils.isNotBlank(username) && StringUtils.isNotBlank(password)) {
                conn = DriverManager.getConnection(url, username, password);
            } else {
                conn = DriverManager.getConnection(url);
            }

            conn.setAutoCommit(false);
        } catch (Exception e) {
            conn = null;
            error = "SqlError in creating connection to " + url + e.toString();
            LOGGER.error(error);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Connection getConn() {
        if (conn == null) {
            connect();
        }
        return conn;
    }

}
