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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface DBHelper {
	public void open() throws SQLException, ClassNotFoundException;
	public void close() throws SQLException;
	public PreparedStatement getPreparedStatement(String sql) throws SQLException;
	public ResultSet executeQuery(String sql) throws SQLException;
	public int executeUpdate(String sql) throws SQLException;
	public boolean execute(String sql) throws SQLException;
}
