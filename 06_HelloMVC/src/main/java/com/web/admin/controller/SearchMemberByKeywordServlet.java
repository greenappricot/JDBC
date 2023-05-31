package com.web.admin.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.admin.model.service.AdminService;
import com.web.member.model.vo.Member;

/**
 * Servlet implementation class SearchMemberByKeywordServlet
 */
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
		// TODO Auto-generated method stub
		String keyword=request.getParameter("searchKeyword");
		String searchType=request.getParameter("searchType");
		System.out.println(keyword+ searchType);
		// userId 검색
		List<Member> m=new AdminService().searchMemberByKeyword(searchType, keyword);
		System.out.println(m);
		HttpSession session=request.getSession();
		session.setAttribute("members", m);
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
