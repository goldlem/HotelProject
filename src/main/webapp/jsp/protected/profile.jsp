<%@ page contentType="text/html;charset=UTF-8"%>
<%@page import="by.urbel.hotel.entity.UserType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>

<fmt:message bundle="${Loc}" key="Locale.name.firstName" var="firstName"/>
<fmt:message bundle="${Loc}" key="Locale.name.lastName" var="lastName"/>
<fmt:message bundle="${Loc}" key="Locale.name.email" var="email"/>
<fmt:message bundle="${Loc}" key="Locale.name.phoneNumber" var="phoneNumber"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.password" var="enterPassword"/>
<fmt:message bundle="${Loc}" key="Locale.placeholder.enter.repeat.password" var="repeatPassword"/>
<fmt:message bundle="${Loc}" key="Locale.button.change.profile" var="changeProfile"/>
<fmt:message bundle="${Loc}" key="Locale.name.reservations" var="nameReservations"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.in" var="nameCheckIn"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.out" var="nameCheckOut"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomNumber" var="nameRoomNumber"/>
<fmt:message bundle="${Loc}" key="Locale.name.categoryName" var="nameCategoryName"/>
<fmt:message bundle="${Loc}" key="Locale.name.totalCost" var="nameTotalCost"/>
<fmt:message bundle="${Loc}" key="Locale.name.reservations.empty" var="emptyReservations"/>
<fmt:message bundle="${Loc}" key="Locale.button.cancel" var="btnCancel"/>
<fmt:message bundle="${Loc}" key="Locale.name.profile" var="nameProfile"/>

<jsp:useBean id="reservations" scope="session" type="java.util.List"/>
<jsp:useBean id="user" scope="session" type="by.urbel.hotel.entity.User"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${nameProfile}</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/profile.css">
</head>
<c:if test="${!user.role.equals(UserType.CLIENT)}">
    <c:redirect url="${pageContext.request.contextPath}/jsp/authorization.jsp"/>
</c:if>
<body>
<div class="container">
    <c:import url="/header"/>
    <section class="profile-info row">
        <form class="col-md-6" method="post" action="${pageContext.request.contextPath}/controller?command=update_user">
                <div class="row mb-3" style="margin-top: 50px">
                    <div class="h1">
                        ${user.surname}
                        ${user.name}
                    </div>
                </div>
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label" for="name">
                            ${firstName}
                        </label>
                        <input type="text" class="form-control" value="${user.name}"
                               name="name" id="name" maxlength="35" required>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label" for="surname">
                            ${lastName}
                        </label>
                        <input type="text" class="form-control" value="${user.surname}"
                               name="surname" id="surname" maxlength="35" required>
                    </div>
                    <div class="col-12">
                        <label class="form-label" for="phoneNumber">
                            ${phoneNumber}
                        </label>
                        <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber"
                               maxlength="12" minlength="4" value="${user.phoneNumber}">
                    </div>
                <div class="col-12">
                    <label class="form-label" for="email">
                        ${email}
                    </label>
                    <input type="email" class="form-control" id="email" name="email"
                           maxlength="255" value="${user.email}">
                </div>
                <div class="col-md-6">
                    <input type="password" name="password" minlength="8" maxlength="20" id="password"
                           class="form-control" placeholder="${enterPassword}" aria-placeholder="${enterPassword}"
                           autocomplete="new-password" required>
                </div>
                <div class="col-md-6">
                    <input type="password" class="form-control" minlength="8" maxlength="20" id="confirm_password"
                           autocomplete="off" placeholder="${repeatPassword}" aria-placeholder="${repeatPassword}"
                           onblur="isPasswordMatch();" required>
                </div>
                <div class="col-12 d-flex justify-content-center">
                    <input type="submit" class="btn btn-outline-dark" value="${changeProfile}">
                </div>
                </div>
        </form>
        <div class="col-md-6 profile-right-image justify-content-center align-items-center d-flex">
        </div>
    </section>
    <section class="reservations row mb-5">
        <p class="fs-1 mb-5">${nameReservations}</p>
        <c:choose>
            <c:when test="${not empty reservations}">
                <table class="table table-hover">
                    <tr>
                        <th>${nameCheckIn}</th>
                        <th>${nameCheckOut}</th>
                        <th>${nameRoomNumber}</th>
                        <th>${nameCategoryName}</th>
                        <th>${nameTotalCost}</th>
                        <th></th>
                    </tr>
                    <c:forEach items="${reservations}" var="reservation">
                        <tr>
                            <td>${reservation.checkInDate}</td>
                            <td>${reservation.checkOutDate}</td>
                            <td>${reservation.room.roomNumber}</td>
                            <td>${reservation.room.category.categoryName}</td>
                            <td>${reservation.totalCost}</td>
                            <td>
                                <form method="post" action="<c:url value="/controller?command=delete_reservation"/>">
                                    <input type="hidden" name="reservationId" value="${reservation.reservationId}">
                                    <input type="submit" class="btn btn-outline-dark" value="${btnCancel}">
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p class="col-12 fs-5 text-center">
                    ${emptyReservations}
                </p>
            </c:otherwise>
        </c:choose>
    </section>
</div>
<c:import url="/footer"/>
</body>
</html>
