<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.web.member.model.dao.MemberDao, java.util.List" %>
<%@ include file="/views/common/header.jsp"%>
<%
	List<Member> members = (List)request.getAttribute("members");
	String type=request.getParameter("searchType");
	String keyword=request.getParameter("searchKeyword"); // 무조건 있는 값은 아니고 처음에 접속할 때는 parameter 없을 수 있다.
	String numPerpage=request.getParameter("numPerpage");
%>
<style>
    section#memberList-container table#tbl-member {width:100%; border:1px solid gray; border-collapse:collapse;}
	section#memberList-container table#tbl-member th, table#tbl-member td {border:1px solid gray; padding:10px; }
	#pageBar{
		padding:20px 0 20px 0;
	}
	section#memberList-container a, span {
		font-size : 18px;
		text-decoration:none;
		padding:0 5px 0 5px;
		color:#555555;
	}
	section#memberList-container span{
		color:black;
		font-weight:bolder;
	}
	div#search-container {
		margin:0 0 10px 0; 
		padding:3px; 
    	background-color: rgba(0, 188, 212, 0.3);
    }
    div#search-userId{display:inline-block;}
    div#search-userName{display:none;}
    div#search-gender{display:none;} 
    div#numPerpage-container{float:right;}
    form#numperPageFrm{display:inline;}
   </style>
	<section id="memberList-container">
        <h2>회원관리</h2>
        <div id="search-container">
        	검색타입 : 
        	<select id="searchType">
        		<option value="userId" <%= type!=null && type.equals("userId")?"selected":""%>>아이디</option>
        		<option value="userName" <%= type!=null && type.equals("userName")?"selected":""%>>회원 이름</option>
        		<option value="gender" <%= type!=null && type.equals("gender")?"selected":""%>>성별</option>
        	</select>
        	<div id="search-userId">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userId" >
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 아이디를 입력하세요"  value="<%=type!=null&&type.equals("userId")?keyword:""%>">
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-userName">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="userName">
        			<input type="text" name="searchKeyword" size="25" 
        			placeholder="검색할 이름을 입력하세요"  value="<%=type!=null&&type.equals("userName")?keyword:""%>">
        			<button type="submit">검색</button>
        		</form>
        	</div>
        	<div id="search-gender">
        		<form action="<%=request.getContextPath()%>/admin/searchMember">
        			<input type="hidden" name="searchType" value="gender">
        			<label><input type="radio" name="searchKeyword" value="M" 
        			<%=type!=null&&type.equals("gender")&&keyword!=null&&keyword.equals("M")?"checked":"" %>>남</label> 
        			<!-- type과 keyword가 같이 넘어가기 때문에 type이 null이 아니라면 keyword가 null일 수 없다.  -->
        			<label><input type="radio" name="searchKeyword" value="F" 
        			<%=type!=null&&type.equals("gender")&&keyword!=null&&keyword.equals("F")?"checked":"" %>>여</label>
        			<button type="submit">검색</button>
        		</form>
        	</div>
        </div>
        <div id="numPerpage-container">
        	페이지당 회원수 : 
        	<!-- 검색 조회한 페이지, 검색 전 페이지와 다르게 주소를 요청해야한다.  -->
        	<!-- <form id="numPerFrm" action="<%=request.getContextPath()%>/admin/memberList.do"> -->
        		<select name="numPerpage" id="numPerpage">
        			<option value="10" <%= numPerpage!=null && numPerpage.equals("10")?"selected":"" %>>10</option>
        			<option value="5" <%= numPerpage!=null && numPerpage.equals("5")?"selected":"" %>>5</option>
        			<option value="3" <%= numPerpage!=null && numPerpage.equals("3")?"selected":"" %>>3</option>
        		</select>
        		<!-- 검색 결과 넘길 때는 cPage, numPerPage 같이 넘어가야하고, 검색 결과 전에는 keyword, type, cPage, numPerPage넘겨야한다. -->
        	<!-- </form> -->
        </div>
        <table id="tbl-member">
            <thead>
                <tr>
                    <th>아이디</th>
		    		<th>이름</th>
				    <th>성별</th>
				    <th>나이</th>
				    <th>이메일</th>
				    <th>전화번호</th>
				    <th>주소</th>
				    <th>취미</th>
				    <th>가입날짜</th>
                </tr>
            </thead>
            <tbody>
            	<%-- <%if(members.isEmpty()){  --%>
                <%if(members!=null){ 
                	for(Member m: members){%>
                	<tr>
                		<td><%=m.getUserId() %></td>
                		<td><%=m.getUserName() %></td>
                		<td><%=m.getGender() %></td>
                		<td><%=m.getAge() %></td>
                		<td><%=m.getEmail() %></td>
                		<td><%=m.getPhone() %></td>
                		<td><%=m.getAddress() %></td>
                		<td><%=m.getHobby()!=null?String.join(",",m.getHobby()):"" %></td><%-- for문 돌려서 출력할 수 있다 --%>
                		<td><%=m.getEnrollDate() %></td>
                	</tr>
                	<%}
                }else{%>
                	<tr><td colspan="9">결과가 없습니다.</td></tr>
                <%} %>
            </tbody>
        </table>
        <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
    </section>
   <script>
   		$("#searchType").change(e=>{
   			const type=$(e.target).val();
   			$(e.target).parent().find("div").css("display","none");
   			$("#search-"+type).css("display","inline-block");
   		});
   		/* // ready 함수 사용해서 searchtype에 일치하는 input 나오게 한다. */
   		$(()=>{
   			$("#searchType").change();
   			
   			$("#numPerpage").change(e=>{
   	 			let url=location.href;
   	 			if(url.includes("?")){
	   	 			url=url.substring(0,url.indexOf("?")+1)
	   	 			+'searchType=<%=type%>'
	   	 			+'&searchKeyword=<%=keyword%>'
	   	 			+'&cPage=<%=request.getParameter("cPage")!=null
	   	 				?request.getParameter("cPage"):1%>'
	   	 			+'&numPerpage='+e.target.value;
   	 			}else{
   	 				url+='?';
   	 				url+='cPage=<%=request.getParameter("cPage")!=null
	   	 				?request.getParameter("cPage"):1%>'
	   	 			+'&numPerpage='+e.target.value;
   	 			}
   	 			//console.log(url);
   	 			//url+='&numPerpage='+e.target.value;
   	 			location.assign(url);
   	 		});
   		})
    </script>
    
<%@ include file="/views/common/footer.jsp"%>