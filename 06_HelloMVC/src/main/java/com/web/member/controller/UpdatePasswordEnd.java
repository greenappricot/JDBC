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

/**
 * Servlet implementation class UpdatePasswordEnd
 */
@WebServlet("/updatePasswordEnd")
public class UpdatePasswordEnd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdatePasswordEnd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId=request.getParameter("userId"); // input hidden으로 적은  userId 넘어옴
		String oriPw=request.getParameter("password");
		String newPw=request.getParameter("password_new");
		Member m=new MemberService().selectByUserIdAndPw(userId,oriPw);
		String msg="",loc="/member/updatePassword.do?userId="+userId; 
		// 새 창열 때 userId값을 같이 보내기 때문에 userId값을 같이 전달하지 않으면 null값 나와서 불일치 계속 뜸 ㅠ 
		if(m==null) {
			// 비밀번호 일치하지 않음
			// 방법 1. 간단하게 하려면 exception 발생시켜서 - 에러페이지로 이동시킨다.
			msg="비밀번호가 일치하지 않습니다";
		}else {
			// 비밀번호 일치함
			int result=new MemberService().updatePassword(userId,newPw);
			if(result>0) {
				msg="비밀번호 수정 완료";
				loc="/";// 수정 완료 후 창 닫기
				// 창 닫히면서 로그아웃 하기 
				// 부모 창에 로그아웃 서블릿 주소 찍어주기 . . . .=> opener, location 접근해서 /logout.do으로 요청 보낸다. 

				// 수정 완료 후에는 창을 닫고 싶을 때 msg.jsp에 닫을지 말지 결정을 servlet에서 결정해서 넘겨준다.
				request.setAttribute("script", "opener.location.replace('"+request.getContextPath()+"/logout.do');close();"); 
//				opener.location.replace(request.getContextPath()/logout.do)
			}else {
				msg="비밀번호 수정 실패";
			}
			request.setAttribute("msg",msg);
			request.setAttribute("loc", loc);
			
			// 수정 완료 후 로그아웃 하기
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}

