package com.renren.Wario.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DBHelper {

	public abstract void open() throws SQLException, ClassNotFoundException;
	public abstract void close() throws SQLException;
	public abstract ResultSet executeQuery(String sql) throws SQLException;
	public abstract int executeUpdate(String sql) throws SQLException;
	public abstract boolean execute(String sql) throws SQLException;
}
