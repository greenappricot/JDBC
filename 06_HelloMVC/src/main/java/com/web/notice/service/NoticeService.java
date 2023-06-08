package com.web.notice.service;

import static com.web.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.web.notice.model.dao.NoticeDao;
import com.web.notice.model.vo.Notice;

public class NoticeService {
	
	private NoticeDao dao=new NoticeDao();
	
	public int selectNoticeCount() {
		Connection conn=getConnection();
		int count=dao.selectNoticeCount(conn);
		close(conn);
		return count;
	}
	
	public List<Notice> selectNotice(int cPage, int numPerpage){
		Connection conn=getConnection();
		List<Notice> list=dao.selectNotice(conn, cPage, numPerpage);
		close(conn);
		return list;
	}
	
	public int insertNotice(Notice n) {
		Connection conn=getConnection();
		int result= dao.insertNotice(conn, n);
		if(result>0) commit(conn);
		else rollback(conn);
		close(conn);
		return result;
	}
	
	public Notice selectNoticeByNo(int no) {
		Connection conn=getConnection();
		Notice n=dao.selectNoticeByNo(conn,no);
		close(conn);
		return n;
	}
	
}
