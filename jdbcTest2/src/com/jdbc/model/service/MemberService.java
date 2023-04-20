package com.jdbc.model.service;

import java.sql.Connection;
import java.util.List;

import com.jdbc.common.JdbcTemplete;
import com.jdbc.model.dao.MemberDao;
import com.jdbc.model.dto.MemberDto;

public class MemberService {
	// Service 클래스의 역할 
	// 1. DB에 연결하는 Connection 객체를 관리한다. (생성 및 삭제)
	// 2. Transaction(Commit, Rollback) 처리
	// 3. Service에 해당하는 DAO클래스를 호출하고, 연결 DB에서 SQL문 실행시킨다.  
	private MemberDao dao= new MemberDao(); // dao클래스와 연결
	
	public List<MemberDto> selectAllMember(){
		Connection conn=JdbcTemplete.getConnection(); // Connection 생성
		List<MemberDto> members= dao.selectAllMember(conn); //dao의 selectAllMember에 connection 반환하여 list에 저장함
		JdbcTemplete.close(conn);
		return members;
	}
	
	public MemberDto selectMemberById(String id) {
		Connection conn= JdbcTemplete.getConnection();
		MemberDto m= dao.selectMemberById(conn, id);
		JdbcTemplete.close(conn);
		return m;
	}
	
	public String insertMember(MemberDto m) {
		Connection conn= JdbcTemplete.getConnection();
		int result=dao.insertMember(conn,m);
		JdbcTemplete.close(conn);
		return result>0? "회원 등 성공 :)" : "회원 등록 실패 :(";
	}
	
	public String updateMember(MemberDto m) {
		Connection conn= JdbcTemplete.getConnection();// connection 생성
		int result= dao.updateMember(conn, m);
		JdbcTemplete.close(conn);
		return result>0? "회원 수정 성공 :)": "회원 수정 실패 :(";
	}
	
}
