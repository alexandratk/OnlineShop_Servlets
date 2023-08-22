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
	<h3>Teams</h3>
	<c:if test="${ currentUser.getRole() eq 'admin'}">
		<p><a href='<c:url value="createTeam" />'>Create new team</a></p>
	</c:if>

	<table class="table">
		<tbody>
			<tr>
	          <th>Id</th>
	          <th>Title</th>
	          <c:if test="${ currentUser.getRole() eq 'admin'}">
	          	<th>*</th>
	          </c:if>
	        </tr>
			<c:forEach items="${requestScope.teams}" var="team">
				<tr>
					<th>
						<c:out value="${team.getId()}"></c:out>
					</th>
					<td>
						<a href="listUsersFromTeam?id=${team.getId()}">
							<c:out value="${team.getTitle()}"></c:out>
						</a>
					</td>
					<c:if test="${ currentUser.getRole() eq 'admin'}">
						<td>
							<a href='<c:url value="editTeam?id=${team.getId()}" />'>Edit</a> | 
							<a href='<c:url value="deleteTeam?id=${team.getId()}" />'>Delete</a>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
