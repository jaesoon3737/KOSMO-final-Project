<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>




<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<h1>User</h1>
<sec:authorize var="loggedIn" access="isAuthenticated()" />
<c:choose>
    <c:when test="${loggedIn}">
        You are logged in ${loggedIn}
    </c:when>
    <c:otherwise>
        You are logged out
    </c:otherwise>
</c:choose>
<sec:authorize access="isAnonymous()">
    <form method="POST" action="<c:url value='j_spring_security_check'/>">
    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
        Username: <input name="j_username" type="text" value="${SPRING_SECURITY_LAST_USERNAME}" /> 
        Password: <input name="j_password" type="password" /> 
        <input type="submit" value="Sign in" />
    </form>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
   <form method="POST" action="/logout">
    <input name="${_csrf.parameterName}" type="hidden" value="${_csrf.token}"/>
         <input type="submit" value="logout" />
    </form>
</sec:authorize>

<security:authorize access="isAuthenticated()">
   Welcome <%= request.getUserPrincipal().getName() %>	
</security:authorize>





</body>
</html>