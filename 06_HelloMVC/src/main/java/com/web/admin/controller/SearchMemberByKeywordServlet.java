package com.web.admin.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.admin.model.service.AdminService;
import com.web.member.model.vo.Member;


@WebServlet("/admin/searchMember")
public class SearchMemberByKeywordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMemberByKeywordServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//클라이언트가 보낸 데이터를 기준으로 Member테이블에서 
		//해당하는 데이터를 조회해서 보내줌
		String type=request.getParameter("searchType");
		String keyword=request.getParameter("searchKeyword");
		
		int cPage,numPerpage;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		try {
			numPerpage=Integer.parseInt(request.getParameter("numPerpage"));
		}catch(NumberFormatException e) {
			numPerpage=5;
		}
		
		List<Member> members=new AdminService()
				.selectMemberByKeyword(type,keyword,cPage,numPerpage);
		
		request.setAttribute("members", members);
		
		String pageBar="";
		int totalData=new AdminService()
				.selectMemberByKeywordCount(type,keyword);
		int totalPage=(int)Math.ceil(totalData/numPerpage);
		int pageBarSize=5;
		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
		int pageEnd=pageNo+pageBarSize-1;
		
		if(pageNo==1) {
			pageBar+="<span>[이전]</span>";
		}else {
			pageBar+="<a href='"+request.getRequestURI()
			+"?searchType="+type
			+"&searchKeyword="+keyword
			+"&cPage="+(pageNo-1)
			+"&numPerpage="+numPerpage+"'>[이전]</a>";
		}
		
		while(!(pageNo>pageEnd||pageNo>totalPage)) {
			if(pageNo==cPage) {
				pageBar+="<span>"+pageNo+"</span>";
			}else {
				pageBar+="<a href='"+request.getRequestURI()
				+"?searchType="+type
				+"&searchKeyword="+keyword
				+"&cPage="+pageNo
				+"&numPerpage="+numPerpage+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			pageBar+="<a href='"+request.getRequestURI()
			+"?searchType="+type
			+"&searchKeyword="+keyword
			+"&cPage="+pageNo
			+"&numPerpage="+numPerpage+"'>[다음]</a>";
		}
		request.setAttribute("pageBar", pageBar);
		request.getRequestDispatcher("/views/admin/memberManage.jsp")
		.forward(request, response);
		
	
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}




//package com.web.admin.controller;

//import java.io.IOException;
//import java.util.List;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import com.web.admin.model.service.AdminService;
//import com.web.member.model.vo.Member;
//
///**
// * Servlet implementation class SearchMemberByKeywordServlet
// */
//@WebServlet("/admin/searchMember")
//public class SearchMemberByKeywordServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//       
//    /**
//     * @see HttpServlet#HttpServlet()
//     */
//    public SearchMemberByKeywordServlet() {
//        super();
//        // TODO Auto-generated constructor stub
//    }
//
//	/**
//	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
//	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// 클라이언트가 보낸 데이터를 기준으로 Member테이블에서 해당하는 데이터 조회해서 보내준다.
//		// TODO Auto-generated method stub
//		String keyword=request.getParameter("searchKeyword");
//		String type=request.getParameter("searchType");
//		
//		// 유동적으로 선택한 숫자에 따라 페이징 처리하기 위한 숫자
//		int cPage, numPerpage;
//		try {
//			cPage=Integer.parseInt(request.getParameter("cPage"));
//		}catch(NumberFormatException e) {
//			cPage=1;
//		}
//		try {
//			numPerpage=Integer.parseInt(request.getParameter("numPerPage"));
//		}catch(NumberFormatException e) {
//			numPerpage=5;
//		}
//		
////		System.out.println(searchType+" "+keyword);
////		List<Member> m=new AdminService().searchMemberByKeyword(searchType, keyword); // 페이징 처리 하기 전 service로 넘기는 로직
//		
//		// 페이징 처리하기 위해서 매개변수로 jsp에서 선택한 cPage, numPerPage 값을 같이 넘겨준다. 
//		List<Member> m=new AdminService().selectMemberByKeyword(type, keyword, cPage, numPerpage);
//		
//		request.setAttribute("members", m);
//		
//		// 페이징할 때 필요한 칭긔들
//		String pageBar="";
//		int totalData= new AdminService().selectMemberByKeywordCount(type, keyword);
//		// 갯수를 셀 때도 필터링 된 값을 가져가야하므로 type, keyword를 매개변수로 넘겨야한다.
//		int totalPage=(int)Math.ceil((double)totalData/numPerpage);
//		int pageBarSize=5; // pageBarSize도 client 요구에 따라 바꿀 수 있다.
//		int pageNo=((cPage-1)/pageBarSize)*pageBarSize+1;
//		int pageEnd=pageNo+pageBarSize-1;
//		
//		if(pageNo==1) {
//			pageBar+="<span>[이전]</span>";
//		}else {
//
//			pageBar+="<a href='"+request.getRequestURI()+"?searchType="+type+"&searchKeyword="+keyword+
//					"&cPage="+(pageNo-1)+"&numPerpage="+numPerpage+"'>[이전]</a>";
//			// 페이징 처리 하면서 이전 페이지 쿼리스트링에 들어가는 내용을 다 입력해줘야한다. 
//		}
//		
//		while(!(pageNo>pageEnd||pageNo>totalPage)) {
//			if(pageNo==cPage) {
//				pageBar+="<span>"+pageNo+"</span>";
//			}else {
//				pageBar+="<a href='"+request.getRequestURI()+"?searchType="+type+"&searchKeyword="+keyword+
//						"&cPage="+(pageNo-1)+"&numPerpage="+numPerpage+"'>"+pageNo+"</a>";
//			}
//			pageNo++;
//		}
//		
//		if(pageNo>totalPage) {
//			pageBar+="<span>[다음]</span>";
//		}else {
//			pageBar+="<a href='"+request.getRequestURI()+"?searchType="+type+"&searchKeyword="+keyword+
//					"&cPage="+(pageNo-1)+"&numPerpage="+numPerpage+"'>[다음]</a>";
//		}
//		
//		request.setAttribute("pageBar", pageBar); // 같은 값을 사용하고 있기 때문에 setAttribute로 pageBar를 넘겨준다.
//		request.getRequestDispatcher("/views/admin/memberManage.jsp").forward(request, response);
//		
//		
//	}
//
//	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// TODO Auto-generated method stub
//		doGet(request, response);
//	}
//
//}
