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
	
//	public List<Member> selectMemberByKeyword(String searchType,String keyword) { 
//		Connection conn=getConnection();
//		List<Member> m=dao.selectMemberByKeyword(conn, searchType, keyword);
//		close(conn);
//		return m;
//	}
	public List<Member> selectMemberByKeyword(String searchType, String keyword, int cPage, int numPerPage) { 
		// 페이징 처리하기 위해 매개변수 추가해줬음 -> 처음에 map으로 매개변수 받았으면 수정할 필요 없음..
		Connection conn=getConnection();
		List<Member> m=dao.selectMemberByKeyword(conn, searchType, keyword, cPage, numPerPage);
		close(conn);
		return m;
	}
	
	public int selectMemberByKeywordCount(String searchType,String keyword) {
		Connection conn=getConnection();
		int count= dao.selectMemberByKeywordCount(conn, searchType, keyword);
		close(conn);
		return count;
	}

}
