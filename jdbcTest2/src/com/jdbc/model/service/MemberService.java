package com.jdbc.model.service;

import java.sql.Connection;

import java.util.List;

import com.jdbc.common.JdbcTemplete;
import com.jdbc.model.dao.MemberDao;
import com.jdbc.model.dto.MemberDto;
import static com.jdbc.common.JdbcTemplete.getConnection; 
// import를 하면 static 상속시 클래스명을 적어서 접근하는 것을 줄일 수 있다. 
import static com.jdbc.common.JdbcTemplete.*;  
// JdbcTemplete에 있는 모든 메소드를 클래스에 접근하지 않고 사용할 수 있다.

public class MemberService {
	// Service 클래스의 역할 
	// 1. DB에 연결하는 Connection 객체를 관리한다. (생성 및 삭제)
	// 2. Transaction(Commit, Rollback) 처리
	// 3. Service에 해당하는 DAO클래스를 호출하고, 연결 DB에서 SQL문 실행시킨다.  
	private MemberDao dao= new MemberDao(); // dao클래스와 연결
	
	public List<MemberDto> selectAllMember(){
		Connection conn=JdbcTemplete.getConnection(); // Connection 생성
		List<MemberDto> members= dao.selectAllMember(conn); //dao의 selectAllMember에 connection 반환하여 list에 저장함
		close(conn); //import static com.jdbc.common.JdbcTemplete.*; 를 import했기 때문에 그냥 접근 가능..! 
		return members;
	}
	
	public MemberDto selectMemberById(String id) {
		Connection conn= getConnection(); // JdbcTemplete.getConnection()으로 호출하지 않아도 오류나지 않는다. 
		MemberDto m= dao.selectMemberById(conn, id);
		close(conn);
		return m;
	}
	
	public List<MemberDto> selectMemberByName(String name) {
		Connection conn= getConnection();
		List<MemberDto> m= dao.selectMemberByName(conn, name);
		close(conn);
		return m;
	}
	
	public int insertMember(MemberDto m) {
		Connection conn= getConnection();
		int result=dao.insertMember(conn,m);
		// 트랜잭션 처리
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int updateMember(MemberDto m) {
		Connection conn= getConnection();// connection 생성
		int result= dao.updateMember(conn, m);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public int deleteMember(String id) {
		Connection conn= getConnection();
		int result= dao.deleteMember(conn, id);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
}
