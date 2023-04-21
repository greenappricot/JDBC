package com.myjdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DeleteJdbcTest {

	public static void main(String[] args) {
		Connection conn= null;
		Statement stmt= null;
		int result=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","BS","BS");
			conn.setAutoCommit(false);
			stmt= conn.createStatement();
			String sql="DELETE DEPARTMENT WHERE DEPT_ID='D0'";
			result=stmt.executeUpdate(sql);
			if(result>0) conn.commit();
			else conn.rollback();
			
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}

	}

}
