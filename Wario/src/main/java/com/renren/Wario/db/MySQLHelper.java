/**
 *    Copyright 2014 Renren.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.renren.Wario.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.renren.Wario.config.ApplicationProperties;

public class MySQLHelper extends DBHelper {
	
	private static final String JDBC = ApplicationProperties.getJdbcDriver();
	private static final String URL = ApplicationProperties.getJdbcUrl();
	private static final String USERNAME = ApplicationProperties.getJdbcUsername();
	private static final String PASSWORD = ApplicationProperties.getJdbcPassword();
	
	private Connection connection;
	private Statement statement;
	
	public MySQLHelper() {
		
	}
	
	@Override
	public void open() throws SQLException, ClassNotFoundException {
		Class.forName(JDBC);
		connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		statement = connection.createStatement();
	}

	@Override
	public void close() throws SQLException {
		statement.close();
		connection.close();
	}

	@Override
	public ResultSet executeQuery(String sql) throws SQLException {
		return statement.executeQuery(sql);
	}

	@Override
	public int executeUpdate(String sql) throws SQLException {
		return statement.executeUpdate(sql);
	}

	@Override
	public boolean execute(String sql) throws SQLException {
		return statement.execute(sql);
	}

}
