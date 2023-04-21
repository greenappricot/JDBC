package com.myjdbc.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.myjdbc.model.vo.Department;

public class MyJdbcTest {
	
	public static void main(String[] args) {
		//Connection - DriverManager
		//Statement
		//ResultSet
		//반환
		Connection conn= null;
		Statement stmt= null;
		ResultSet rs= null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","BS","BS");
			conn.setAutoCommit(false);
//			System.out.println("접속 가능");
			
			String sql= "SELECT * FROM DEPARTMENT";
			stmt= conn.createStatement();
			rs= stmt.executeQuery(sql);
//			System.out.println(rs);
			List<Department> dList= new ArrayList<Department>();
			while(rs.next()) {
				Department d= new Department();
				d.setDeptId(rs.getString("dept_id"));
				d.setDeptTitle(rs.getString("dept_title"));
				d.setLocationId(rs.getString("location_id"));
				dList.add(d);
			}
			dList.forEach((d)->System.out.println(d));
			
			
			
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}catch (SQLException e){
			e.printStackTrace();
		}finally {
			try {
				if(rs!=null) rs.close();
				if(stmt!=null) stmt.close();
				if(conn!=null) conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
