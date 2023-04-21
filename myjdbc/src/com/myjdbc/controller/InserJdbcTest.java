package com.myjdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class InserJdbcTest {

	public static void main(String[] args) {
		
		Connection conn= null;
		Statement stmt= null;
		int result=0;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","BS","BS");
			conn.setAutoCommit(false);
			System.out.println("접속완료");
			
			stmt= conn.createStatement();
//			String sql="INSERT INTO DEPARTMENT VALUES('D0','강사부','L2')";
//			int result= stmt.executeUpdate(sql); 한 줄로 표현할 수 있다.
			result= stmt.executeUpdate("INSERT INTO DEPARTMENT VALUES('D0','강사부','L2')");
			if(result>0) conn.commit();
			else conn.rollback();
//			System.out.println(result);
			
			
		}catch(ClassNotFoundException e){
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
