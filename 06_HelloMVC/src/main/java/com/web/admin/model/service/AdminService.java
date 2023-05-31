package com.web.admin.model.service;

import static com.web.common.JDBCTemplate.close;
import static com.web.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import com.web.admin.model.dao.AdminDao;
import com.web.member.model.vo.Member;

public class AdminService {
	private AdminDao dao=new AdminDao();
	public List<Member> searchMemberAll(int cpage,int numPerPage) {
		Connection conn=getConnection();
		List<Member> members=dao.searchMemberAll(conn,cpage,numPerPage);
		close(conn);
		return members;
	}
	
	public int selectMemberCount() {
		Connection conn=getConnection();
		int result=dao.selectMemberCount(conn);
		close(conn);
		return result;
	}
	
	public List<Member> searchMemberByKeyword(String searchType,String keyword) {
		Connection conn=getConnection();
		List<Member> m=dao.searchMemberByKeyword(conn, searchType, keyword);
		close(conn);
		return m;
	}
	public List<Member> searchMemberById(String keyword) {
		Connection conn=getConnection();
		List<Member> m=dao.searchMemberById(conn, keyword);
		close(conn);
		return m;
	}
	
}
