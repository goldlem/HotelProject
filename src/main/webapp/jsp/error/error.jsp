<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:import url="/header"/>
<div class="container text-center mt-5">
    <c:if test="${not empty requestScope.error}">
        <p class="h1">${requestScope.error}</p>
    </c:if>
</div>
<c:import url="/footer"/>
</body>
</html>
