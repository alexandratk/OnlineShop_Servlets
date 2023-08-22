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
		<p><a href='<c:url value="listUsers" />'>-- List users</a></p>
	</div>
		<hr>
		<form method="post">
			<h3>New user</h3>
			<label>Login</label><br>
			<input name="login"/><br><br>
			<label>Password</label><br>
			<input name="password"/><br><br>
			<label>Full name</label><br>
			<input name="fullname"/><br><br>
			<label>Role</label><br>
			<select name="role">
				<option value="user">user</option>
				<option value="admin">admin</option>
			</select><br><br>
			<label>Add team</label><br>
			<select name="team">
				<option value="-1"></option>
				<c:forEach items="${allTeams}" var="team">
					<option value="${team.getId()}">${team.getTitle()}</option>
				</c:forEach>
			</select><br><br>
			<input type="submit" value="Create" />
		</form>
	</body>
</html>
