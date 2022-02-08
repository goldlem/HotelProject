<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="by.urbel.hotel.entity.UserType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.addingRoom" var="addingRoom"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomNumber" var="roomNumber"/>
<fmt:message bundle="${Loc}" key="Locale.name.categoryName" var="categoryName"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomStatus" var="roomStatus"/>
<fmt:message bundle="${Loc}" key="Locale.button.add" var="btnAdd"/>

<jsp:useBean id="categories" scope="session" type="java.util.List"/>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${addingRoom}</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
</head>
<body>
<c:import url="/header"/>
<c:choose>
    <c:when test="${sessionScope.user.role.equals(UserType.ADMIN)}">
        <div class="container" style="margin-top: 70px">
            <c:if test="${not empty sessionScope.error}">
                <div class="alert alert-danger alert-dismissible" role="alert">
                        ${sessionScope.error}
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"
                            onclick="${sessionScope.remove("error")}"></button>
                </div>
            </c:if>
            <div class="row text-center">
                <div class="col-12 h1">
                        ${addingRoom}
                </div>
            </div>
            <div class="d-flex justify-content-center mt-3">
                <form action="${pageContext.request.contextPath}/controller?command=add_new_room" method="post">
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="roomNumber">
                            ${roomNumber}
                        </label>
                        <div class="col-12">
                            <input type="number" class="form-control" id="roomNumber" name="roomNumber"
                                   min="1" max="200" required>
                        </div>
                    </div>
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="categoryName">
                           ${categoryName}
                        </label>
                        <div class="col-12">
                            <select name="categoryName" id="categoryName" class="form-select" required>
                                <c:forEach items="${categories}" var="category">
                                    <option value="${category.categoryName}">${category.categoryName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="roomStatus">
                            ${roomStatus}
                        </label>
                        <div class="col-12">
                            <select class="form-select" name="roomStatus" id="roomStatus" required>
                                <option selected value="available">Available</option>
                                <option value="blocked">Blocked</option>
                            </select>
                        </div>
                    </div>
                    <div class="row m-2 mt-4">
                        <div class="col-12">
                            <input type="submit" class="form-control" value="${btnAdd}">
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <c:redirect url="${pageContext.request.contextPath}"/>
    </c:otherwise>
</c:choose>
<c:import url="/footer"/>
</body>
</html>
