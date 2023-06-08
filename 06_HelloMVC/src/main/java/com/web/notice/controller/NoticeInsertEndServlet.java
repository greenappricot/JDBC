package com.web.notice.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.web.notice.model.vo.Notice;
import com.web.notice.service.NoticeService;

/**
 * Servlet implementation class InserNoticeServlet
 */
@WebServlet("/notice/inserNotice.do")
public class NoticeInsertEndServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertEndServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// file 업로드 처리하기 (클라이언트가 올린 파일을 서버 컴퓨터에 저장하는 개념) -> cos.jar 라이브러리가 제공하는 클래스를 이용한다.
		// 1. form enctype 속성을 multipart/form-data 형식의 요청인지 확인한다.
		if(!ServletFileUpload.isMultipartContent(request)) {// 요청이 multipartcontent인지 확인하는 메소드
			request.setAttribute("msg", "잘못된 접근입니다. 관리자에게 문의하세요 :(");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		// 2. data upload 처리하기
		// 	  -> cos.jar에서 제공하는 MultipartRequest클래스를 이용해서 처리한다.
		// MultipartRequest 클래스를 생성하면 request에 담겨있는 데이터(binary file)를 지정된 위치에 저장한다.
		
		// 매개변수 있는 생성자를 이용해서 생성한다.
		// 1 : HttpServletRequest (클라이언트의 요청을 기반으로 하기 때문에)
		// 2 : 파일을 저장할 위치 설정 -> 절대 경로로 가져온다 -> String
		// 3 : 업로드 파일의 최대 크기 설정 -> int (byte가 최소 단위/ 1 byte = 1024)  1024(byte)*1024(MB)*1024(GB)*1024(TB)
		// 4 : 인코딩 방식 -> String(utf-8)
		// 5 : rename 규칙 설정(클래스) -> 기본 제공 클래스인(DefaultFileRenamePolicy)를 이용한다 (재정의해서 사용할 수 있음)
		//     rename 하는 이유 : 불특정 다수에게 제공하는 서비스이기 때문에, 중복을 방지하기 위해 서버에 저장하는 파일명이랑 업로드 파일명을 다르게 관리한다.
		
		// String으로 저장할 위치(절대 경로) 가져오기 (참고 getResource() -> class buildPath 위치 가져옴)
		// ServletContext 객체를 이용해서 웹 애플리케이션의 절대 경로를 가져올 수 있다
//		String path=getServletContext().getRealPath("/upload/notice");  getServletContext().getRealPath("/") : webapp까지의 경로를 불러온다.
		String path=getServletContext().getRealPath("/upload/notice");
		System.out.println(path);
		
		// 최대 파일 크기 설정
		int maxSize=1024*1024*100; // 100MB
		
		// 인코딩 설정
		String encode="UTF-8";
		
		// rename 클래스 생성하기
		DefaultFileRenamePolicy dfr=new DefaultFileRenamePolicy();
		
		// MultipartRequest 클래스 생성하기 -> 지정된 위치에 업로드 된 파일을 저장시킨다.
		MultipartRequest mr= new MultipartRequest(request,path,maxSize,encode,dfr);
		
		// 클라이언트가 보낸 데이터는 생성된 MultipartRequest 객체를 이용해서 가져온다. -> getParameter("name")
		String noticeTitle=mr.getParameter("noticeTitle");
		String noticeWriter= mr.getParameter("noticeWriter");
		String noticeContent= mr.getParameter("noticeContent");
		// MultipartRequest 객체를 이용해서 저장된 파일에 대한 정보도 가져올 수 있다.
		// 원본 파일명, 재정의 파일명 정보를 가져올 수 있다.
		String oriFileName=mr.getOriginalFileName("upFile");
		String renameFileName= mr.getFilesystemName("upFile");
		
//		System.out.println(noticeTitle+" "+noticeWriter+" "+noticeContent+" "+oriFileName+" "+renameFileName);
		Notice n=Notice.builder().noticeTitle(noticeTitle).noticeWriter(noticeWriter).noticeContent(noticeContent).filePath(renameFileName).build();
		
		int result=new NoticeService().insertNotice(n);
		String msg="공지사항 등록 완료", loc="/notice/noticeList.do";
		if(result==0) {
			msg="공지사항 등록 실패";
			loc="/notice/insertForm.do";
		}
		request.setAttribute("msg", msg);
		request.setAttribute("loc", loc);
		
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
