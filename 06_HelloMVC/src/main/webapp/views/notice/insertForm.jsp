<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/views/common/header.jsp" %>
  <section id="notice-container">
  	<h2>공지사항 작성</h2>
    <form action="<%=request.getContextPath()%>/notice/inserNotice.do" method="post" enctype="multipart/form-data">
        <table id="tbl-notice">
        <tr>
            <th>제 목</th>
            <td><input type="text" name="noticeTitle" required></td> <!-- 작성자 타이틀 반드시 작성하게 하려면 required 속성준다 -->
        </tr>
        <tr>
            <th>작성자</th>
            <td><input type="text" name="noticeWriter" value="<%=loginMember.getUserId()%>" readonly></td>
        </tr>
        <tr>
            <th>첨부파일</th>
            <td><input type="file" name="upFile"></td>
        </tr>
        <tr>
            <th>내 용</th>
            <td><textarea cols="42" rows="5" name="noticeContent"></textarea></td>
        </tr>
        <tr>
            <th colspan="2">
                <input type="submit" value="등록하기" onclick=""><!-- onclick으로 설정값 없으면 넘어가지 못하게 분기처리 -->
            </th>
        </tr>
    </table>
    </form>
    </section>
    <style>
    section#notice-container{width:600px; margin:2% auto; text-align:center;}
    section#notice-container h2{margin:10px 0;}
    table#tbl-notice{width:500px; margin:0 auto; border:1px solid black; border-collapse:collapse; clear:both; }
    table#tbl-notice th {width: 125px; border:1px solid; padding: 5px 0; text-align:center;} 
    table#tbl-notice td {border:1px solid; padding: 5px 0 5px 10px; text-align:left;}
    </style>

<%@ include file="/views/common/footer.jsp" %>	