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
	<h3>Users</h3>
	<c:if test="${ currentUser.getRole() eq 'admin'}">
		<p><a href='<c:url value="createUser" />'>Create new user</a></p>
	</c:if>
	<table>
		<tbody>
		    <tr>
	          <th>Id</th>
	          <th>Login</th>
	          <th>Full name</th>
	          <th>Teams</th>
	          <c:if test="${ currentUser.getRole() eq 'admin'}">
	          	<th>*</th>
	          </c:if>
	        </tr>
			<c:forEach items="${requestScope.users}" var="user">
				<tr>
					<th scope="row" class="align-middle" >
						<c:out value="${user.getId()}"></c:out>
					</th>
					<td>
						<c:out value="${user.getLogin()}"></c:out>
					</td>
					<td>
						<c:out value="${user.getFullname()}"></c:out>
					</td>
					<td>
						<c:out value="${user.teamsToString()}"></c:out>
					</td>
					<c:if test="${ currentUser.getRole() eq 'admin'}">
						<td>
							<a href='<c:url value="editUser?id=${user.getId()}" />'>Edit</a> |
							<a href='<c:url value="deleteUser?id=${user.getId()}" />'>Delete</a>
						</td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>
