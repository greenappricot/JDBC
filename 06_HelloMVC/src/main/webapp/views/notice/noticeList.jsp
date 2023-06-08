<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>
<%@ page import="java.util.List,com.web.notice.model.vo.Notice" %>
<%
	List<Notice> notices= (List)request.getAttribute("notices");
%>
<style>
    section#notice-container{width:600px; margin:3% auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:100%; margin:0 auto; border:1px solid black; border-collapse:collapse;}
    table#tbl-notice th, table#tbl-notice td {border:1px solid; padding: 5px 0; text-align:center;} 
    #pageBar a, span {
		font-size : 18px;
		text-decoration:none;
		padding:0 5px 0 5px;
		color:#555555;
	}
	#pageBar span{
		color:black;
		font-weight:bolder;
	}
	section#notice-container div>button{
		display:flex;
		justify-content:end;
	}
	section#notice-container a {text-decoration:none; color:black;}
	section#notice-container a:hover {font-weight:bold;}
</style>
<section id="notice-container">
        <h2>공지사항</h2>
        <div>
        	<%if(loginMember!=null&&loginMember.getUserId().equals("admin")){ %><!-- admin만 글쓰기 할 수 있게 분기처리 -->
	        	<button onclick="location.assign('<%=request.getContextPath()%>/notice/insertForm.do')">글쓰기</button>
	        <%} %>
        </div>
        <table id="tbl-notice">
            <tr>
                <th>번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>첨부파일</th>
                <th>작성일</th>
            </tr>
            <%if(notices.isEmpty()){ %>
            	<tr>
            		<td colspan="5">조회된 공지사항이 없습니다.</td>
            	</tr>
            <%}else{ 
            	for(Notice n: notices){%>
            		<tr>
            			<td><%=n.getNoticeNo() %></td>
            			<td>
            				<a href="<%=request.getContextPath()%>/notice/noticeView.do?no=<%=n.getNoticeNo()%>"><%=n.getNoticeTitle() %></a>
            			</td>
            			<td><%=n.getNoticeWriter() %></td>
            			<td>
							<%if(n.getFilePath()!=null){ %>
								<img src="<%=request.getContextPath()%>/images/file.png" width="20">
							<%}else{%>
								출력할 파일이 없습니다.
							<%} %>
						</td>
            			<td><%=n.getNoticeDate() %></td>
            		</tr>
            	<%} 
            }%>
        </table>
        <div id="pageBar">
        	<%=request.getAttribute("pageBar") %>
        </div>
    </section>
<%@ include file="/views/common/footer.jsp" %>