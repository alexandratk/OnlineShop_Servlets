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
			<h3>Edit user</h3>
			<label>Full name</label><br>
			<input name="fullname" value="${editUser.getFullname()}"/><br><br>
			<label>Add team</label><br>
			<select name="team">
				<option value="-1"></option>
				<c:forEach items="${allTeams}" var="team">
					<option value="${team.getId()}">${team.getTitle()}</option>
				</c:forEach>
			</select><br><br>
			<input type="submit" value="Save" />
		</form>
		<table class="table">
		<tbody>
			<c:forEach items="${editUser.getTeams()}" var="team">
				<tr id="Groups_${team.getId()}">
					<th id="groupid-${team.getId()}" scope="row" class="align-middle" >
						<c:out value="${team.getId()}"></c:out>
					</th>
					<td id="groupname-${team.getId()}" class="align-middle">
						<a href="listUsersFromTeam?id=${team.getId()}">
							<c:out value="${team.getTitle()}"></c:out>
						</a>
					</td>
					<c:if test="${ currentUser.getRole() eq 'admin'}">
						<td>
						    <form class="form_button" method="post" action='<c:url value="excludeUserFromTeamServlet" />' style="display:inline;">
						        <input type="hidden" name="user_id" value="${editUser.getId()}">
						        <input type="hidden" name="team_id" value="${team.getId()}">
						        <input type="submit" value="Exclude">
						    </form>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</body>
</html>
