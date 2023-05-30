package com.web.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.member.model.service.MemberService;
import com.web.member.model.vo.Member;

@WebServlet("/member/updateEndMemeber.do")
public class MemberUpdate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MemberUpdate() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// 회원 정보 수정하는 서비스
		//1. 클라이언트가 보낸 데이터를 받아오기
		Member m= Member.builder().userId(request.getParameter("userId")).userName(request.getParameter("userName")).age(Integer.parseInt(request.getParameter("age")))
				.gender(request.getParameter("gender").charAt(0)).email(request.getParameter("email")).phone(request.getParameter("phone")).address(request.getParameter("address"))
				.hobby(request.getParameterValues("hobby")).build();
		//2. 회원정보를 수정해줌(DB에 있는 데이터)
		int result=new MemberService().updateMember(m);
		//3. 결과를 전송하기
		String msg="", loc="";
		if(result>0) {
			msg="회원정보가 수정되었습니다.";
			loc="/";
			HttpSession session=request.getSession();
			session.setAttribute("loginMember", new MemberService().selectByUserId(m.getUserId()));
		}else {
			msg="회원정보 수정 실패했습니다. 다시 시도하세요";
			//loc="/views/member/memberView.jsp";
			loc="/member/memberView.do?userId="+m.getUserId(); // servlet으로 요청해야 msg alert 뜨고 페이지 에러가 나지 않는다.
			// 이 servlet에서 userId를 파라미터로 받고 있기 때문에 userId를 같이 보내야 에러나지 않는다.
		}
		request.setAttribute("msg",msg);
		request.setAttribute("loc", loc);
		request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
