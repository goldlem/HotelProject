<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ page import="by.urbel.hotel.entity.UserType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.hotel" var="hotelName"/>
<fmt:message bundle="${Loc}" key="Locale.link.home" var="homeLink"/>
<fmt:message bundle="${Loc}" key="Locale.link.log.out" var="logOut"/>
<fmt:message bundle="${Loc}" key="Locale.link.profile" var="profileLink"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.in" var="signIn"/>
<fmt:message bundle="${Loc}" key="Locale.link.sign.up" var="signUp"/>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <title>Header</title>
</head>
<body>
<div class="container">
    <header class="row">
        <nav class="navbar navbar-expand-xl navbar-light sticky-top">
            <a href="${pageContext.request.contextPath}" class="navbar-brand">${hotelName}</a>
            <div class="changeLocaleInterface" style="margin-left: 30px; margin-right: 30px">
                <a class="link-secondary" href="?sessionLocale=en">EN</a>
                <a class="link-secondary" href="?sessionLocale=ru">RU</a>
            </div>

            <button class="navbar-toggler" role="button" data-bs-toggle="collapse" data-bs-target="#collapsibleNavbar"
                    aria-expanded="false" aria-controls="collapsibleNavbar">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="collapsibleNavbar">
                <ul class="navbar-nav mr-auto smooth-scroll">
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/index.jsp" class="nav-link">${homeLink}</a>
                    </li>
                    <c:choose>
                    <c:when test="${sessionScope.user.role.equals(UserType.ADMIN)}">
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/controller?command=logout"
                           class="nav-link">${logOut}</a>
                    </li>
                    </c:when>
                    <c:when test="${sessionScope.user.role.equals(UserType.CLIENT)}">
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/controller?command=go_to_profile_page"
                           class="nav-link">${profileLink}</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/controller?command=logout"
                           class="nav-link">${logOut}</a>
                    </li>
                    </c:when>
                    <c:otherwise>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/jsp/registration.jsp"
                           class="nav-link">${signUp}</a>
                    </li>
                    <li class="nav-item">
                        <a href="${pageContext.request.contextPath}/jsp/authorization.jsp"
                           class="nav-link">${signIn}</a>
                    </li>
                    </c:otherwise>
                    </c:choose>
            </div>
        </nav>
    </header>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>
</body>
</html>