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

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet(name="memberList", urlPatterns="/admin/memberList.do")
public class MemberListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 페이징 처리하기
		int cPage;
		try {
			cPage=Integer.parseInt(request.getParameter("cPage"));
		}catch(NumberFormatException e) {
			cPage=1;
		}
		int numPerPage=10;
		// 1. db에서 member 테이블에서 데이터 가져오기
		List<Member> members=new AdminService().searchMemberAll(cPage,numPerPage);
		// 2. db에서 가져온 데이터 저장(화면출력)
		request.setAttribute("members", members);
		// 3. 페이지 바 구성
		//	1) db에 저장된 전체 데이터 수 가져오기
		int totalData= new AdminService().selectMemberCount();
		//	2) 전체 페이지 수를 계산하기 * 소수점 주의 totalData/numPerPage 값을 올림해서 int로 형변환한다.
		int totalPage= (int)Math.ceil((double)totalData/numPerPage);
		int pageBarSize= 5;
		//	3) 페이지바 시작 번호 계산하기 -> 시작 번호, 끝 번호 설정해놓고 끝번호 만날 때까지 1씩 증가하게 한다. -> 반복문
		int pageNo= ((cPage-1)/pageBarSize)*pageBarSize+1; // (ex < 1 2 3 4 5 > 에서 1)
		int pageEnd=pageNo+pageBarSize-1; // 페이지바에서 끝나는 페이지번호를 저장한다. (ex < 1 2 3 4 5 > 에서 5)
		//	4) 페이지바를 구성하는 html 저장하기 builder나 append하는 것이 효율적이다.
		String pageBar="";
		
		// 		페이지바에서 이전 표시하기
		if(pageNo==1) { // 페이지가 1페이지라면 이전으로 이동 x 
			pageBar+="<span>[이전]</span>";
		}else {
			pageBar+="<a href='"+request.getRequestURI()+"?cPage="+(pageNo-1)+"'>[이전]</a>";
		}
		//		선택할 페이지 번호 출력하기
		while(!(pageNo>pageEnd||pageNo>totalPage)) { // pageEnd보다 크지 않거나 totalPage보다 크지 않으면 돌아라
			if(pageNo==cPage) { // 지금 보고 있는 페이지와 출력할 페이지가 같으면 현재 페이지 클릭할 수 없게 한다.
				pageBar+="<span>"+pageNo+"</span>";
			}else { // 나머지 숫자는 a 태그로 작성한다.
				pageBar+="<a href='"+request.getRequestURI()+"?cPage="+pageNo+"'>"+pageNo+"</a>";
			}
			pageNo++;
		}
		
		//		페이지바에서 다음 표시하기
		if(pageNo>totalPage) {
			pageBar+="<span>[다음]</span>";
		}else {
			pageBar+="<a href='"+request.getRequestURI()+"?cPage="+pageNo+"'>[다음]</a>"; 
			// 이미 pageNo가 while 반복문을 통해 +1된 상태이므로 +1 안 해줘도 된다.
		}
		request.setAttribute("pageBar", pageBar);
		request.getRequestDispatcher("/views/admin/memberManage.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
