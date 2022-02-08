<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.sign.in" var="logIn"/>
<fmt:message bundle="${Loc}" key="Locale.name.email" var="email"/>
<fmt:message bundle="${Loc}" key="Locale.name.password" var="password"/>
<fmt:message bundle="${Loc}" key="Locale.name.rememberMe" var="rememberMe"/>
<fmt:message bundle="${Loc}" key="Locale.button.sign.in" var="signInBtn"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:import url="/header"/>
<main class="container mt-4" style="height: 70%;">
    <div class="row d-flex justify-content-center align-items-center">
        <img src="${pageContext.request.contextPath}/img/404.jpg" alt="404image" style="max-height: 45%; max-width: 45%">
    </div>
</main>
<c:import url="/footer"/>
</body>
</html>
