<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="by.urbel.hotel.entity.UserType" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.in" var="checkIn"/>
<fmt:message bundle="${Loc}" key="Locale.name.check.out" var="checkOut"/>
<fmt:message bundle="${Loc}" key="Locale.name.bedsCount" var="bedsCount"/>
<fmt:message bundle="${Loc}" key="Locale.button.search" var="searchBtn"/>
<fmt:message bundle="${Loc}" key="Locale.button.rooms" var="btnRooms"/>
<fmt:message bundle="${Loc}" key="Locale.button.categories" var="btnCategories"/>
<fmt:message bundle="${Loc}" key="Locale.button.reservations" var="btnReservations"/>
<fmt:message bundle="${Loc}" key="Locale.button.add.room" var="btnAddRoom"/>
<fmt:message bundle="${Loc}" key="Locale.button.add.category" var="btnAddCategory"/>
<fmt:message bundle="${Loc}" key="Locale.name.services.and.conveniences" var="servicesAndConveniences"/>
<fmt:message bundle="${Loc}" key="Locale.name.services.description" var="servicesDescription"/>
<fmt:message bundle="${Loc}" key="Locale.name.services.cleaning" var="serviceCleaning"/>
<fmt:message bundle="${Loc}" key="Locale.name.services.wifi" var="serviceWifi"/>
<fmt:message bundle="${Loc}" key="Locale.name.services.parking" var="serviceParking"/>
<fmt:message bundle="${Loc}" key="Locale.name.services.breakfast" var="serviceBreakfast"/>

<jsp:useBean id="nowDate" class="java.util.Date"/>

<%!
    Date takeTomorrowDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }
%>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MyHotel</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<c:import url="/header"/>
<section class="intro container-fluid">
    <form action="${pageContext.request.contextPath}/controller"
          method="get" class="container col-11 col-sm-4 bg-light" style="border-radius: 5px">
        <input type="hidden" name="command" value="find_free_categories_by_beds_number">
        <div class="row p-3">
            <div class="col">
                <label for="start">${checkIn}</label>
                <input type="date" name="checkInDate" id="start" class="form-control"
                       value="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd"/>"
                       max="3000-12-30" required
                       min="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd"/>"
                >
            </div>
        </div>
        <div class="row p-3">
            <div class="col">
                <label for="end">${checkOut}</label>
                <input type="date" name="checkOutDate" id="end" class="form-control"
                       value="<fmt:formatDate value="<%=takeTomorrowDate()%>" pattern="yyyy-MM-dd"/>"
                       max="3000-12-31" required
                       min="<fmt:formatDate value="${nowDate}" pattern="yyyy-MM-dd"/>"
                >
            </div>
        </div>
        <div class="row m-2">
            <label class="form-label" for="beds">${bedsCount}</label>
            <div class="col-12">
                <input type="number" step="1" min="1" max="4" id="beds" class="form-control"
                       value="1" name="bedsCount" required>

            </div>
        </div>
        <div class="row p-3">
            <input type="submit" value="${searchBtn}" class="form-control">
        </div>
    </form>
</section>
<div class="container">
    <c:if test="${sessionScope.user.role.equals(UserType.ADMIN)}">
        <section class="row">
            <form class="col-auto col m-auto" action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="find_all_rooms">
                <input type="submit" value="${btnRooms}" class="btn btn-outline-dark m-2">
            </form>
            <form class="col-auto col m-auto" action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="find_all_categories">
                <input type="submit" value="${btnCategories}" class="btn btn-outline-dark m-2">
            </form>
            <form class="col-auto col m-auto" action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="find_all_reservations">
                <input type="submit" value="${btnReservations}" class="btn btn-outline-dark m-2">
            </form>
            <form class="col-auto m-auto" action="${pageContext.request.contextPath}/controller" method="get">
                <input type="hidden" name="command" value="go_to_add_room_page">
                <input type="submit" value="${btnAddRoom}" class="btn btn-outline-dark m-2">
            </form>
            <form class="col-auto col m-auto" action="${pageContext.request.contextPath}/jsp/admin/addNewCategory.jsp"
                  method="get">
                <input type="submit" value="${btnAddCategory}" class="btn btn-outline-dark m-2">
            </form>
        </section>
    </c:if>
    <section class="services row p-3">
        <div class="container">
            <div class="row pb-5">
                <div class="services__text"><h2 class="title am am--2 title--h2">${servicesAndConveniences}</h2>
                    <p class="am am--4">${servicesDescription}</p></div>
            </div>
            <div class="row">
                <div class="item col-lg-3 col-sm-6 col-12">
                    <div class="service-item">
                        <div class="service-icon">
                            <img class="service-img"
                                 src="${pageContext.request.contextPath}/img/free-services/parking.png">
                        </div>
                        <p class="service-name">${serviceParking}</p>
                    </div>
                </div>
                <div class="item col-lg-3 col-sm-6 col-12">
                    <div class="service-item">
                        <div class="service-icon">
                            <img class="service-img"
                                 src="${pageContext.request.contextPath}/img/free-services/wifi.png">
                        </div>
                        <p class="service-name">${serviceWifi}</p>
                    </div>
                </div>
                <div class="item col-lg-3 col-sm-6 col-12">
                    <div class="service-item">
                        <div class="service-icon">
                            <img class="service-img"
                                 src="${pageContext.request.contextPath}/img/free-services/cleaning.png">
                        </div>
                        <p class="service-name">${serviceCleaning}</p>
                    </div>
                </div>
                <div class="item col-lg-3 col-sm-6 col-12">
                    <div class="service-item">
                        <div class="service-icon">
                            <img class="service-img"
                                 src="${pageContext.request.contextPath}/img/free-services/breakfast.png">
                        </div>
                        <p class="service-name">${serviceBreakfast}</p>
                    </div>
                </div>
            </div>
        </div>
    </section>
</div>
<c:import url="/footer"/>
</body>
</html>