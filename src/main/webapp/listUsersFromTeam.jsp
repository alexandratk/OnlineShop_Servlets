<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style> 
<%@include file="./WEB-INF/css/styles.css"%>
</style>
<html>
<body>
	<c:if test="${not empty sessionScope.currentUser}">
		<div class="header">
			You are logged as ${sessionScope.currentUser.getRole()} ${sessionScope.currentUser.getFullname()},
			<a href='<c:url value="Login" />'>logout</a>
		</div>
	</c:if>
	<div class="left_header">
		<p><a href='<c:url value="listTeams" />'>List teams --</a></p>
	</div>
		<hr>
	<table class="table">
		<tbody>
			<tr>
	          <th>Id</th>
	          <th>Login</th>
	          <th>Full name</th>
	        </tr>
			<c:forEach items="${requestScope.users}" var="user">
				<tr>
					<th>
						<c:out value="${user.getId()}"></c:out>
					</th>
					<td>
						<c:out value="${user.getLogin()}"></c:out>
					</td>
					<td>
						<c:out value="${user.getFullname()}"></c:out>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
