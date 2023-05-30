<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 변경</title>
<style>
    div#updatePassword-container{
        background:red;
    }
    div#updatePassword-container table {
        margin:0 auto;
        border-spacing: 20px;
    }
    div#updatePassword-container table tr:last-of-type td {
        text-align:center;
    }
    </style>
</head>
<body>
	<div id="updatePassword-container">
		<form name="updatePwdFrm" action="<%=request.getContextPath()%>/updatePasswordEnd" method="post" >
			<table>
				<tr>
					<th>현재 비밀번호</th>
					<td><input type="password" name="password" id="password" required></td>
				</tr>
				<tr>
					<th>변경할 비밀번호</th>
					<td>
						<input type="password" name="password_new" id="password_new" required>
					</td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>	
						<input type="password" id="password_chk" required><br>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="hidden" name="userId" value="<%=request.getParameter("userId") %>"> <!-- 화면에 보이지는 않지만 userId를 저장해둔다. -->
						<%-- <input type="hidden" name="userId" value="<%=session.getAttribute("userID") %>"> session에 있을 때는 이렇게 불러올 수 있다-> filter로 접근 못 하게 막아야함. --%>
						<input type="submit" value="변경" />&nbsp;
						<input type="button" value="닫기" />						
					</td>
				</tr>
			</table>
			
		</form>
	</div>
</body>
</html>