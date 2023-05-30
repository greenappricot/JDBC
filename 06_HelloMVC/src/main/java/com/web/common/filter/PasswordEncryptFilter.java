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

/**
 * Servlet Filter implementation class PasswordEncriptoFilter
 */
@WebFilter(urlPatterns={"/member/*"},
			servletNames= {"login"}) // member로 시작하는 전체
public class PasswordEncryptFilter extends HttpFilter implements Filter {
    public PasswordEncryptFilter() {
        super();
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// 암호화 처리하는 로직 만들기 
		// password 호출 했을 때 암호화처리하기 -> password를 getParameter()를 통해서 가져올 수 있다. 
		// getParameter()메소드를 재정의해서 사용한다. -> wrapper 사용하기
		PasswordEncryptWrapper pwew=new PasswordEncryptWrapper((HttpServletRequest)request); // wrapper 생성

		// pass the request along the filter chain
		chain.doFilter(pwew, response);
		// 이 필터를 거치는 데이터가 전부 wrapper 클래스 적용된다.
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
