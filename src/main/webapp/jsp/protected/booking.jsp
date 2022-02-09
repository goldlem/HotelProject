<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="by.urbel.hotel.entity.UserType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.bedsCount" var="nameBedsCount"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomPrice" var="nameRoomPrice"/>
<fmt:message bundle="${Loc}" key="Locale.button.book" var="btnBook"/>
<fmt:message bundle="${Loc}" key="Locale.name.rooms.empty" var="nameRoomsEmpty"/>

<jsp:useBean id="categories" scope="session" type="java.util.List"/>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Booking</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/booking.css">
    <c:if test="${empty categories}">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
    </c:if>
</head>
<body>
<c:import url="/header"/>
<c:choose>
    <c:when test="${sessionScope.user.role.equals(UserType.CLIENT) || sessionScope.user.role.equals(UserType.ADMIN)}">
        <div class="container" style="margin-top: 70px">
            <c:choose>
                <c:when test="${empty categories}">
                    <div class="row text-center">
                        <p class="fs-3">${nameRoomsEmpty}</p>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${categories}" var="category">
                        <div class="row category-row">
                            <div class="card mb-3 category-card" style="max-width: 800px">
                                <div class="row g-0">
                                    <div class="col-md-5 p-2 d-flex align-content-center">
                                        <div id="${category.categoryName.replace(" ","")}" class="carousel slide"
                                             data-bs-ride="carousel">
                                            <div class="carousel-inner">
                                                <div class="carousel-item active">
                                                    <img src="${pageContext.request.contextPath}/${category.photoPaths[0]}"
                                                         class="d-block category-image" alt="categoryImage">
                                                </div>
                                                <div class="carousel-item">
                                                    <img src="${pageContext.request.contextPath}/${category.photoPaths[1]}"
                                                         class="d-block category-image" alt="categoryImage">
                                                </div>
                                            </div>
                                            <button class="carousel-control-prev" type="button"
                                                    data-bs-target="#${category.categoryName.replace(" ","")}"
                                                    data-bs-slide="prev">
                                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                                <span class="visually-hidden">Previous</span>
                                            </button>
                                            <button class="carousel-control-next" type="button"
                                                    data-bs-target="#${category.categoryName.replace(" ","")}"
                                                    data-bs-slide="next">
                                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                                <span class="visually-hidden">Next</span>
                                            </button>
                                        </div>
                                    </div>
                                    <div class="col-md-7">
                                        <div class="card-body">
                                            <h5 class="card-title">${category.categoryName}</h5>
                                            <c:choose>
                                                <c:when test="${sessionScope.locale.equals('ru')}">
                                                    <p class="card-text">${category.descriptionRu}</p>
                                                </c:when>
                                                <c:when test="${sessionScope.locale.equals('en')}">
                                                    <p class="card-text">${category.descriptionEn}</p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p class="card-text">${category.descriptionRu}</p>
                                                </c:otherwise>
                                            </c:choose>
                                            <p class="card-text">${nameBedsCount}: ${category.bedsCount}</p>
                                            <div class="row">
                                                <div class="col-6"> ${nameRoomPrice}: ${category.roomPrice}$</div>
                                                <c:if test="${sessionScope.user.role.equals(UserType.CLIENT)}">
                                                    <form method="post" class="col-6"
                                                          action="${pageContext.request.contextPath}/controller?command=reserve_room">
                                                        <input type="submit" class="btn btn-primary"
                                                               name="update-button" value="${btnBook}">
                                                        <input type="hidden" name="categoryName"
                                                               value="${category.categoryName}">
                                                    </form>
                                                </c:if>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </c:when>
    <c:otherwise>
        <c:redirect url="/jsp/authorization.jsp"/>
    </c:otherwise>
</c:choose>
<c:import url="/footer"/>
</body>
</html>
