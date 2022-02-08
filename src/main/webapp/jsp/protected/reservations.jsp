<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="by.urbel.hotel.entity.UserType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.reservations" var="nameReservations"/>
<fmt:message bundle="${Loc}" key="Locale.name.firstName" var="nameFirstName"/>
<fmt:message bundle="${Loc}" key="Locale.name.lastName" var="nameLastName"/>
<fmt:message bundle="${Loc}" key="Locale.name.email" var="nameEmail"/>
<fmt:message bundle="${Loc}" key="Locale.name.phoneNumber" var="namePhoneNumber"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.in" var="nameCheckIn"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.out" var="nameCheckOut"/>
<fmt:message bundle="${Loc}" key="Locale.name.bookingDate" var="nameBookingDate"/>
<fmt:message bundle="${Loc}" key="Locale.name.totalCost" var="nameTotalCost"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomNumber" var="nameRoomNumber"/>
<fmt:message bundle="${Loc}" key="Locale.button.delete" var="btnDelete"/>
<fmt:message bundle="${Loc}" key="Locale.name.reservations.empty" var="nameReservationsEmpty"/>

<jsp:useBean id="reservations" scope="session" type="java.util.List"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${nameReservations}</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:import url="/header"/>
<c:choose>
    <c:when test="${sessionScope.user.role.equals(UserType.ADMIN)}">
        <div class="container">
            <p class="row h1 mt-5 mb-5">${nameReservations}</p>
            <c:choose>
                <c:when test="${not empty reservations}">
                    <section class="row">
                        <table class="table table-hover">
                            <tr>
                                <th>${nameEmail}</th>
                                <th>${namePhoneNumber}</th>
                                <th>${nameLastName}</th>
                                <th>${nameFirstName}</th>
                                <th>${nameTotalCost}</th>
                                <th>${nameBookingDate}</th>
                                <th>${nameCheckIn}</th>
                                <th>${nameCheckOut}</th>
                                <th>${nameRoomNumber}</th>
                                <th></th>
                            </tr>
                            <c:forEach items="${reservations}" var="reservation">
                                <tr>
                                    <td>${reservation.user.email}</td>
                                    <td>${reservation.user.phoneNumber}</td>
                                    <td>${reservation.user.surname}</td>
                                    <td>${reservation.user.name}</td>
                                    <td>${reservation.totalCost}</td>
                                    <td>${reservation.bookingDate}</td>
                                    <td>${reservation.checkInDate}</td>
                                    <td>${reservation.checkOutDate}</td>
                                    <td>${reservation.room.roomNumber}</td>
                                    <td>
                                        <form method="post"
                                              action="<c:url value="/controller?command=delete_reservation"/>">
                                            <input type="hidden" name="reservationId"
                                                   value="${reservation.reservationId}">
                                            <input type="submit" class="btn btn-outline-dark" value="${btnDelete}">
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                        </table>
                    </section>
                </c:when>
                <c:otherwise>
                    <p class="row fs-5">
                        ${nameReservationsEmpty}
                    </p>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:otherwise>
        <c:redirect url="${pageContext.request.contextPath}"/>
    </c:otherwise>
</c:choose>
<c:import url="/footer"/>
</body>
</html>