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

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sing In</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/authorization.css">
</head>
<body>
<c:import url="/header"/>
<div class="container main-container" style="margin-top: 150px">
    <section class="row h2 m-2 d-flex justify-content-center">
            ${logIn}
    </section>
    <section class="row align-items-center  d-flex justify-content-center">
        <form class="form-sign-in col-12 col-sm-5 col-md-4" action="${pageContext.request.contextPath}/controller?command=authorize"
              method="get">
            <input type="hidden" name="command" value="authorize">
            <div class="form-group">
                <label for="email">${email}</label>
                <input type="email" class="form-control" id="email" name="email" placeholder="email@example.com" required>
            </div>
            <div class="form-group">
                <label for="password">${password}</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password" required>
            </div>
            <div class="form-check">
                <input type="checkbox" class="form-check-input" id="rememberUser" name="rememberUser" checked>
                <label class="form-check-label" for="rememberUser">
                    ${rememberMe}
                </label>
            </div>
                <input type="submit" class="btn btn-primary mt-1 " value="${signInBtn}">

        </form>
    </section>
    <c:if test="${not empty sessionScope.error}">
        <div class="alert alert-danger alert-dismissible mt-4" role="alert">
                ${sessionScope.error}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"
                    onclick="${sessionScope.remove("error")}"></button>
        </div>
    </c:if>
</div>
<c:import url="/footer"/>
</body>
</html>
