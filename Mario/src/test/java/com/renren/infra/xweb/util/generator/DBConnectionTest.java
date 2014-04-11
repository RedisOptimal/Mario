package com.renren.infra.xweb.util.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.renren.infra.xweb.util.generator.db.DBConnection;
import com.renren.infra.xweb.util.generator.db.SqlColumn;

public class DBConnectionTest {

    private String schema = "root";

    private String table = "xweb_test";

    @Test
    public void testDBConnection() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConn();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet columns = metaData.getColumns(null, schema, table, null);
        List<SqlColumn> sqlColumns = new ArrayList<SqlColumn>();

        String COLUMN_NAME = "COLUMN_NAME";
        String DATA_TYPE = "DATA_TYPE";
        String TYPE_NAME = "TYPE_NAME";
        String COLUMN_SIZE = "COLUMN_SIZE";
        String DECIMAL_DIGITS = "DECIMAL_DIGITS";
        String NULLABLE = "NULLABLE";
        String COLUMN_DEF = "COLUMN_DEF";
        String REMARKS = "REMARKS";

        while (columns.next()) {
            String colname = columns.getString(COLUMN_NAME);
            short coltype = columns.getShort(DATA_TYPE);
            int colsize = columns.getInt(COLUMN_SIZE);
            int digits = 0;
            String coltypname = columns.getString(TYPE_NAME).toUpperCase();
            boolean nullable = false;
            boolean withDefault = true;
            String remark = columns.getString(REMARKS);

            if (coltype == Types.DECIMAL || coltype == Types.NUMERIC) {
                digits = columns.getInt(DECIMAL_DIGITS);
            }
            if (columns.getInt(NULLABLE) == DatabaseMetaData.columnNullable) nullable = true;
            else nullable = false;
            withDefault = (columns.getString(COLUMN_DEF) != null);

            sqlColumns.add(new SqlColumn(colname, colname, coltype, colsize, digits, coltypname,
                    nullable, withDefault, remark));
        }

        columns.close();

        assertTrue(sqlColumns.size() > 0);
        assertEquals(3, sqlColumns.size());
    }

    @Test
    public void testPrimaryKey() throws SQLException {
        DBConnection dbConnection = new DBConnection();
        Connection connection = dbConnection.getConn();

        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet pkeys = metaData.getPrimaryKeys(null, schema, table);
        List<SqlColumn> primaryKeys = new ArrayList<SqlColumn>();

        String PK_NAME = "PK_NAME";

        while (pkeys.next()) {
            String pkname = pkeys.getString(PK_NAME);
            primaryKeys.add(new SqlColumn(pkname, pkname, (short) 0, 0, 0, "", false, false, ""));
        }
        pkeys.close();

        assertTrue(primaryKeys.size() > 0);
        assertEquals(1, primaryKeys.size());
    }

}
