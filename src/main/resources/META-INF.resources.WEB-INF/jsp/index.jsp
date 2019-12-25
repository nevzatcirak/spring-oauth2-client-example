<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页</title>
</head>
<body>
	<h1>首页</h1>
	<!-- 
	<div>
		<a href="${pageContext.request.contextPath}/logout" style="float:right;">退出</a>
	</div>
	 -->
	<div>
		<h3>OAuth2客户端授权&获取Joke列表</h3>
		<ul>
			<li><a href="${pageContext.request.contextPath}/jokes?grant_type=client_credentials">Client Credentials</a></li>
			<li><a href="${pageContext.request.contextPath}/jokes?grant_type=authorization_code">Authorization Code</a></li>
			<li>
				<a href="javascript:submitOAuth2PasswordForm();">Resource Owner Password Credentials</a>
				<div style="margin-top:15px;">
					<form name="oauth2PasswordForm" action="${pageContext.request.contextPath}/jokes" method="get">
						<input type="hidden" name="grant_type" value="password"/>
						<label>Username：<input type="text" name="username" placeholder="monkey"/></label>
						<label>Password：<input type="password" name="password" placeholder="123456"/></label>
					</form>
				</div>
			</li>
		</ul>
	</div>
	<script type="text/javascript">
		function submitOAuth2PasswordForm() {
			var username = document.oauth2PasswordForm.username;
			var password = document.oauth2PasswordForm.password;
			if(!username.value){
				alert("请输入username");
				username.focus();
				return;
			}
			if(!password.value){
				alert("请输入password");
				password.focus();
				return;
			}
			document.oauth2PasswordForm.submit();
		}
	</script>
</body>
</html>