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
		<form method="post">
			<h3>Edit team</h3>
			<label>Title</label><br>
			<input name="title" value="${editTeam.getTitle()}"/><br><br>
			<input type="submit" value="Save" />
		</form>
	</body>
</html>
