package com.jdbc.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTemplete {
	// 기능 제공용 클래스 -> 공용이므로 Static으로 선언해서 사용한다.
	// -> Statement, ResultSet -> Dao 클래스에서 사용. Connection, Transaction-> service 클래스에서 사용 
	
	// Connection 객체를 생성해주는 기능을 제공
	public static Connection getConnection() {
		Connection conn= null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn= DriverManager.getConnection("jdbc:oracle:thin:@goodee.ddns.net:10000","BS","BS");
			conn.setAutoCommit(false);
			
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}return conn;
	}
	
	// Connection, Statement, ResultSet 객체를 닫아주는(반환하는) 기능 제공 
	// 1. Connection을 닫아주는 메소드
	public static void close(Connection conn) { 
		try {
			if(conn!=null && !conn.isClosed()) conn.close(); 
			// connection이 null값이 아니거나 닫혀있지 않으면 닫아준다.
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	// 2. Statement 닫아주는 메소드
	public static void close(Statement stmt) {
		try {
			if(stmt!=null && !stmt.isClosed()) stmt.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	// 3. ResultSet 닫아주는 메소드
	public static void close(ResultSet rs) {
		try {
			if(rs!=null && !rs.isClosed()) rs.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	// Transaction 처리하는 기능 제공
	public static void commit(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.commit();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			if(conn!=null && !conn.isClosed()) conn.rollback();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}//
	
}
