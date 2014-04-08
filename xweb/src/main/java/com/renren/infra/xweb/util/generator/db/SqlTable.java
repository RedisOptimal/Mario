package com.renren.infra.xweb.util.generator.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * sql表
 * 
 * @author yong.cao
 * 
 */
public class SqlTable {
	
	private static final String COLUMN_NAME = "COLUMN_NAME";
	private static final String DATA_TYPE = "DATA_TYPE";
	private static final String TYPE_NAME = "TYPE_NAME";
	private static final String COLUMN_SIZE = "COLUMN_SIZE";
	private static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
	private static final String NULLABLE = "NULLABLE";
	private static final String COLUMN_DEF = "COLUMN_DEF";
	private static final String REMARKS = "REMARKS";      

	private String schema;
	private String tablename;
	private String entityname;

	private List<SqlColumn> sqlColumns = Lists.newArrayList();
	private HashMap<String, SqlColumn> allColumns = Maps.newHashMap();
	private List<String> typeList = Lists.newArrayList();
	private List<SqlColumn> primaryKeys = Lists.newArrayList();

	public SqlTable(String schema, String tablename) {

		this.schema = schema.trim();
		this.tablename = tablename.trim();
		this.entityname = tablename.trim();

		getTableInfoFromDatabase();

	}

	/**
	 * 从数据库获取table信息
	 */
	private void getTableInfoFromDatabase() {
		DBConnection dbConnection = new DBConnection();
		Connection connection = dbConnection.getConn();

		try {
			DatabaseMetaData metaData = connection.getMetaData();
			ResultSet columns = metaData.getColumns(null, schema, tablename,
					null);

			// get all columns info
			while (columns.next()) {
				String colname = columns.getString(COLUMN_NAME);
				short coltype = columns.getShort(DATA_TYPE);
				int colsize = columns.getInt(COLUMN_SIZE);
				int digits = 0;
				String coltypename = columns.getString(TYPE_NAME).toUpperCase();
				boolean nullable = false;
				boolean isContainDefault = true;
				String remark = columns.getString(REMARKS);

				if (coltype == Types.DECIMAL || coltype == Types.NUMERIC) {
					digits = columns.getInt(DECIMAL_DIGITS);
				}
				if (columns.getInt(NULLABLE) == DatabaseMetaData.columnNullable)
					nullable = true;
				else
					nullable = false;
				isContainDefault = (columns.getString(COLUMN_DEF) != null);

				SqlColumn column = new SqlColumn(colname, colname, coltype,
						colsize, digits, coltypename, nullable,
						isContainDefault, remark);
				addColumn(column);

			}

			columns.close();

			// get index information
			getIndexInfo(metaData);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				connection = null;
				e.printStackTrace();
			}
		}

	}

	/**
	 * 获取主键信息
	 * 
	 * @param metaData
	 * @throws SQLException 
	 */
	private void getIndexInfo(DatabaseMetaData metaData) throws SQLException {
        ResultSet pkeys = metaData.getPrimaryKeys(null, schema, tablename);

        while (pkeys.next()) {
            String pkeystr = pkeys.getString(COLUMN_NAME);
            SqlColumn x = (SqlColumn) allColumns.get(pkeystr);
            x.setKey(true);
            primaryKeys.add(x);
        }
        pkeys.close();
	}

	/**
	 * 添加列
	 * 
	 * @param column
	 */
	private void addColumn(SqlColumn column) {
		allColumns.put(column.getColName(), column);
		sqlColumns.add(column);

		String attrType = column.getAttrType();
		if (StringUtils.containsIgnoreCase(attrType, "java.")) {
			if (!typeList.contains(attrType)) {
				typeList.add(attrType);
			}
		}
	}

	public String getSchema() {
		return schema;
	}

	public String getTablename() {
		return tablename;
	}

	public String getEntityname() {
		return entityname;
	}

	public List<SqlColumn> getSqlColumns() {
		return sqlColumns;
	}

	public HashMap<String, SqlColumn> getAllColumns() {
		return allColumns;
	}

	public List<String> getTypeList() {
		return typeList;
	}

	public List<SqlColumn> getPrimaryKeys() {
		return primaryKeys;
	}

	public boolean getHasCompositeKey() {
		return getPrimaryKeys().size() > 1;
	}

	public boolean getHasSingleKey() {
		return (getPrimaryKeys().size()) == 1;
	}
}
