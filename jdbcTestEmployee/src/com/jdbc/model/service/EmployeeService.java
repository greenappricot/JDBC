package com.jdbc.model.service;

import static com.jdbc.common.JdbcTemplete.getConnection;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.jdbc.model.dao.EmployeeDao;
import com.jdbc.model.dto.Employee;

public class EmployeeService {
	// 1. DB에 연결하는 Connection 객체를 관리한다. (생성 및 삭제)
	// 2. Transaction(Commit, Rollback) 처리
	// 3. Service에 해당하는 DAO클래스를 호출하고, 연결 DB에서 SQL문 실행시킨다.  
	private EmployeeDao dao= new EmployeeDao();
	
	public void selectAllEmployee() {
		Connection conn=getConnection();
//		List<Employee> e= service.
		
	}
	
}
