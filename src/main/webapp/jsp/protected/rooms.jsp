<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="by.urbel.hotel.entity.RoomStatus" %>
<%@ page import="by.urbel.hotel.entity.UserType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomNumber" var="roomNumber"/>
<fmt:message bundle="${Loc}" key="Locale.name.categoryName" var="categoryName"/>
<fmt:message bundle="${Loc}" key="Locale.name.bedsCount" var="bedsCount"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomPrice" var="roomPrice"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomStatus" var="roomStatus"/>
<fmt:message bundle="${Loc}" key="Locale.button.delete" var="btnDelete"/>
<fmt:message bundle="${Loc}" key="Locale.name.blocked" var="blocked"/>
<fmt:message bundle="${Loc}" key="Locale.name.unblocked" var="unblocked"/>
<fmt:message bundle="${Loc}" key="Locale.name.rooms" var="nameRooms"/>


<jsp:useBean id="rooms" scope="session" type="java.util.List"/>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${nameRooms}</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/index.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<c:import url="/header"/>
<c:choose>
    <c:when test="${sessionScope.user.role.equals(UserType.ADMIN)}">
        <section class="container">
            <div class="row">
                <p class="col h1 mt-5">${nameRooms}</p>
                <div class="col">
                    <c:if test="${not empty sessionScope.error}">
                        <div class="col alert alert-danger alert-dismissible" role="alert">
                                ${sessionScope.error}
                            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"
                                    onclick="${sessionScope.remove("error")}"></button>
                        </div>
                    </c:if>
                </div>
            </div>
            <table class="table table-hover">
                <tr>
                    <th>${roomNumber}</th>
                    <th>${categoryName}</th>
                    <th>${bedsCount}</th>
                    <th>${roomPrice}</th>
                    <th>${roomStatus}</th>
                    <th></th>

                </tr>
                <c:forEach items="${rooms}" var="room">
                    <tr>
                        <td>${room.roomNumber}</td>
                        <td>${room.category.categoryName}</td>
                        <td>${room.category.bedsCount}</td>
                        <td>${room.category.roomPrice}</td>
                        <td>
                            <form method="post">
                                <input type="hidden" name="roomNumber" value="${room.roomNumber}">
                                <c:if test="${room.status.equals(RoomStatus.BLOCKED)}">
                                    <input type="submit" class="btn btn-outline-dark" name="unblock-button"
                                           value="${blocked}"
                                           formaction="${pageContext.request.contextPath}/controller?command=unblock_room">
                                </c:if>
                                <c:if test="${room.status.equals(RoomStatus.AVAILABLE)}">
                                    <input type="submit" class="btn btn-outline-dark" name="block-button"
                                           value="${unblocked}"
                                           formaction="${pageContext.request.contextPath}/controller?command=block_room">
                                </c:if>
                            </form>
                        </td>
                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/controller?command=delete_room">
                                <input type="submit" class="btn btn-outline-dark" name="delete-button"
                                       value="${btnDelete}">
                                <input type="hidden" name="roomNumber" value="${room.roomNumber}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </c:when>
    <c:otherwise>
        <c:redirect url="${pageContext.request.contextPath}"/>
    </c:otherwise>
</c:choose>
<c:import url="/footer"/>
</body>
</html>
