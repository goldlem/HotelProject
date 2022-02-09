<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>

<fmt:message bundle="${Loc}" key="Locale.name.registration" var="registration"/>
<fmt:message bundle="${Loc}" key="Locale.name.firstName" var="firstName"/>
<fmt:message bundle="${Loc}" key="Locale.name.lastName" var="lastName"/>
<fmt:message bundle="${Loc}" key="Locale.name.email" var="email"/>
<fmt:message bundle="${Loc}" key="Locale.name.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.password" var="enterPassword"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.repeat.password" var="repeatPassword"/>
<fmt:message bundle="${Loc}" key="Locale.button.sign.up" var="signUp"/>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Registration</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
</head>
<body>
<c:import url="/header"/>
<div class="container" style="margin-top: 100px">
    <div class="row text-center">
        <div class="col-12 h1">
            ${registration}
        </div>
    </div>
    <div class="d-flex justify-content-center mt-3">
        <form class="col-md-4" onsubmit="return isPasswordMatch()" method="post"
              action="${pageContext.request.contextPath}/controller?command=register">
            <div class="row g-3">
                <div class="col-md-6">
                    <input type="text" class="form-control" placeholder="${firstName}" aria-label="${firstName}"
                           name="name" maxlength="35" required>
                </div>
                <div class="col-md-6">
                    <input type="text" class="form-control" placeholder="${lastName}" aria-label="${lastName}"
                           name="surname" maxlength="35" required>
                </div>
                <div class="col-md-12">
                    <input type="email" class="form-control" placeholder="${email}" aria-label="${email}"
                           name="email" id="email" maxlength="255" required>
                </div>
                <div class="col-md-12">
                    <input type="tel" class="form-control" placeholder="${phoneNumber}" aria-label="${phoneNumber}"
                           name="phoneNumber" id="phoneNumber" maxlength="13" minlength="3"
                           pattern="+[0-9]{12}" required>
                </div>
                <div class="col-md-6">
                    <input type="password" name="password" minlength="8" maxlength="20" id="password"
                           class="form-control" placeholder="${enterPassword}" aria-label="${enterPassword}"
                           autocomplete="new-password" required>
                </div>
                <div class="col-md-6">
                    <input type="password" class="form-control" minlength="8" maxlength="20" id="confirm_password"
                           autocomplete="off" placeholder="${repeatPassword}" aria-label="${repeatPassword}" required
                           onblur="isPasswordMatch();">
                </div>
                <div class="col-12 d-flex justify-content-center">
                    <input type="submit" class="btn btn-outline-dark" value="${signUp}">
                </div>
            </div>
        </form>
    </div>
</div>
<c:import url="/footer" charEncoding="UTF-8"/>
<script type="text/javascript" src="${pageContext.request.contextPath}../js/PasswordsValidation.js"></script>
</body>
</html>