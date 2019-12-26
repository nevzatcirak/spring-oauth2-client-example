<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Joke列表</title>
</head>
<body>
	<div>
		<h3>Joke列表</h3>
		<c:if test="${not empty requestScope.jokeList}">
			<table>
				<tbody>
					<c:forEach items="${requestScope.jokeList}" var="item">
						<tr>
							<td>${item}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>