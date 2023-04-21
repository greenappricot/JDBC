package com.jdbc.common;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcTemplete {
	// 기능 제공용 클래스 -> 공용이므로 Static으로 선언해서 사용한다.
	// -> Statement, ResultSet -> Dao 클래스에서 사용. Connection, Transaction-> service 클래스에서 사용 
	
//	// Connection 객체를 생성해주는 기능을 제공
//	public static Connection getConnection() {
//		Connection conn= null;
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","STUDENT","STUDENT");
//			conn.setAutoCommit(false);
//			
//		}catch(ClassNotFoundException e) {
//			e.printStackTrace();
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}return conn;
//	}
	
	// Connection 객체를 생성해주는 기능을 제공
		public static Connection getConnection() {
			Connection conn= null;
			// resources - driver.properties 파일을 호출 -> 절대 경로가 필요하다. -> Navigator 패널로 경로 확인 가능하다 
			String path=JdbcTemplete.class.getResource("/driver.properties").getPath();
//			System.out.println(path); //  /C:/Users/GDJ/Downloads/0418/jdbcTest2_hr/bin/ bin 폴더 안의 실질적인 경로가 출력된다 
			try {
				Properties driver= new Properties();
				driver.load(new FileReader(path));
				Class.forName(driver.getProperty("drivername"));
				conn= DriverManager.getConnection(driver.getProperty("url"),driver.getProperty("user"),driver.getProperty("pw"));
				// 연결되는 driver 관련된 정보를 보호할 수 있다.
				// 파일로 관리하기 때문에, 설정값(접속하는 url이나 계정...)을 쉽게 변경할 수 있다. 
				conn.setAutoCommit(false);
				
			}catch(ClassNotFoundException e) {
				e.printStackTrace();
			}catch(SQLException e) {
				e.printStackTrace();
			}catch(IOException e) {
				e.printStackTrace();
			}
			return conn;
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
