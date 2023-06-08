package com.web.admin.model.dao;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.member.model.vo.Member;
import static com.web.common.JDBCTemplate.*;
import static com.web.member.model.dao.MemberDao.getMember;

public class AdminDao {
	
	Properties sql=new Properties();
	{
		String path=AdminDao.class.getResource("/sql/admin/adminsql.properties").getPath();
		try(FileReader fr=new FileReader(path)){
			sql.load(fr);
		}catch(IOException e) {
			e.printStackTrace();
		}
	} // 기본 생성자 안에 선언해도 된다. 
	
	public List<Member> searchMemberAll(Connection conn, int cpage,int numPerPage) {
		List<Member> m= new ArrayList<Member>();
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("searchMemberAll"));
			pstmt.setInt(1, (cpage-1)*numPerPage+1);
			pstmt.setInt(2, cpage*numPerPage);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				m.add(getMember(rs));
//				 MemberDao에 있는 getMember를 static 메소드로 만들어서 접근할 수 있다.
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return m;
	}
	
	public int selectMemberCount(Connection conn) {
		PreparedStatement pstmt=null;
		ResultSet rs=null; // select문 써야하니까 resultset이 필요하다.
		int result=0;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectMemberCount"));
			rs=pstmt.executeQuery();
			while(rs.next()) {
				result=rs.getInt(1); // 조회된 가상 컬럼의 index로 결과값을 가져올 수 있다. -> count 값 반환
				// sql문에서 SELECT COUNT(*) FROM MEMBER 말고, 가상컬럼에 값을 지정해주면 그 컬럼명으로 값을 가져올 수 있다. 
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
//	public List<Member> selectMemberByKeyword(Connection conn, String searchType, String keyword) { // 페이징 처리 전 
//		List<Member> m=new ArrayList<Member>();
//		PreparedStatement pstmt=null;
//		ResultSet rs=null;
//		String query=this.sql.getProperty("selectMemberByKeyword").replace("#COL", searchType);
//		try {
//			pstmt=conn.prepareStatement(query);
//			pstmt.setString(1, searchType.equals("gender")?keyword:"%"+keyword+"%"); // gender에는 %없이 M, F 찾기 때문에 분기처리해준다.
//			rs=pstmt.executeQuery();
//			while(rs.next()) m.add(getMember(rs));
//		}catch(SQLException e) {
//			e.printStackTrace();
//		}finally {
//			close(rs);
//			close(pstmt);
//		}return m;
//	}
	
	public List<Member> selectMemberByKeyword(Connection conn, String searchType, String keyword, int cPage, int numPerpage) {
		List<Member> m=new ArrayList<Member>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query=this.sql.getProperty("selectMemberByKeyword").replace("#COL", searchType);
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, searchType.equals("gender")?keyword:"%"+keyword+"%"); 
			pstmt.setInt(2, (cPage-1)*numPerpage+1);
			pstmt.setInt(3, cPage*numPerpage);
			rs=pstmt.executeQuery();
			while(rs.next()) m.add(getMember(rs));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return m;
	}
	
	public int selectMemberByKeywordCount(Connection conn, String searchType, String keyword) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String query=sql.getProperty("selectMemberByKeywordCount").replace("#COL", searchType);
		try {
			pstmt=conn.prepareStatement(query);
			pstmt.setString(1, searchType.equals("gender")?keyword:"%"+keyword+"%");
			rs=pstmt.executeQuery(); // select 문으로 쿼리 실행하기 때문에 ResultSet으로 executeQuery 해야한다.
			if(rs.next()) {
				result=rs.getInt(1); // 결과값 저장하기
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return result;
	}
	
	

	
	
	
}
