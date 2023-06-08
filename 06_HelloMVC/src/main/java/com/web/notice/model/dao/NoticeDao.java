package com.web.notice.model.dao;

import static com.web.common.JDBCTemplate.close;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.web.notice.model.vo.Notice;

public class NoticeDao {
	
	private Properties sql= new Properties();
	public NoticeDao() {
		String path=NoticeDao.class.getResource("/sql/notice/noticesql.properties").getPath();
		try {
			sql.load(new FileReader(path));
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private Notice getNotice(ResultSet rs) throws SQLException {
		return Notice.builder().noticeNo(rs.getInt("notice_no")).noticeTitle(rs.getString("notice_title")).noticeWriter(rs.getString("notice_writer"))
				.noticeContent(rs.getString("notice_content")).noticeDate(rs.getDate("notice_date")).filePath(rs.getString("filepath")).build();
	}
	
	
	public List<Notice> selectNotice(Connection conn, int cPage, int numPerpage) {
		List<Notice> list= new ArrayList<Notice>();
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectNotice"));
			pstmt.setInt(1, (cPage-1)*numPerpage+1);
			pstmt.setInt(2, cPage*numPerpage);
			rs=pstmt.executeQuery();
			while(rs.next()) list.add(getNotice(rs));
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return list;
	}
	
	public int selectNoticeCount(Connection conn) {
		int result=0;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectNoticeCount"));
			rs=pstmt.executeQuery();
			if(rs.next()) result=rs.getInt("RN"); // sql구문에서 별칭으로 준 RN으로 컬럼을 조회할 수 있다.
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}
		return result;
	}

	public int insertNotice(Connection conn, Notice n) {
		int result=0;
		PreparedStatement pstmt=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("insertNotice"));
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeWriter());
			pstmt.setString(3, n.getNoticeTitle());
			pstmt.setString(4, n.getFilePath());
			result=pstmt.executeUpdate();
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(pstmt);
		}return result;
	}
	
	public Notice selectNoticeByNo(Connection conn, int no) {
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		Notice n=null;
		try {
			pstmt=conn.prepareStatement(sql.getProperty("selectNoticeByNo"));
			pstmt.setInt(1, no);
			rs=pstmt.executeQuery();
			while(rs.next()) n=getNotice(rs);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			close(rs);
			close(pstmt);
		}return n;
	}
	
	
}
