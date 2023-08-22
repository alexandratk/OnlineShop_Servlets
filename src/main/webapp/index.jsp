<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style> 
<%@include file="./WEB-INF/css/styles.css"%>
</style>
<html>
<body>
	<c:if test="${not empty param.errorMessage}">
		<h3>${param.errorMessage}</h3>
	</c:if>
	
    <form action="Login" method="post">
        <input name="login"> <br>
        <input name="password" type="password"> <br>
        <input type="submit" value="Login"> <br>
    </form>
</body>
</html>
