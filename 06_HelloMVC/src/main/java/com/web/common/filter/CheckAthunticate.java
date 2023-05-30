package com.web.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.web.member.model.vo.Member;

@WebFilter(servletNames= {
		"memberView"//MemberView Servlet에 이름 등록해서 불러온다.		
})
public class CheckAthunticate extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public CheckAthunticate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		// 로그인 여부, 로그인한 게 나 자신인지 확인하는 필터
		
		HttpSession session= ((HttpServletRequest)request).getSession();// request를 HttpServletRequest로 형변화해서 session 생성한다.
		Member loginMember= (Member)session.getAttribute("loginMember");// 가져온 attribute를 Member로 형변환한다.
		if(loginMember==null||!loginMember.getUserId().equals(request.getParameter("userId"))) {
			// null이거나 일치하지 않으면 
			request.setAttribute("msg", "잘못된 접근입니다 ;(");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/views/common/msg.jsp").forward(request, response);
			
			// throws 처리해서 내 예외 페이지 만들어서 예외처리할 수 있다.
//			throw new RuntimeException("잘못된 접근입니다 ;(");
		}else {
			chain.doFilter(request, response);
		}
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
