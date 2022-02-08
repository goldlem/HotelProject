<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="by.urbel.hotel.entity.UserType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.categories" var="nameCategories"/>
<fmt:message bundle="${Loc}" key="Locale.name.categoryName" var="nameCategoryName"/>
<fmt:message bundle="${Loc}" key="Locale.name.bedsCount" var="nameBedsCount"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomPrice" var="nameRoomPrice"/>
<fmt:message bundle="${Loc}" key="Locale.button.change" var="btnChange"/>
<fmt:message bundle="${Loc}" key="Locale.button.delete" var="btnDelete"/>

<jsp:useBean id="categories" scope="session" type="java.util.List"/>
<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${nameCategories}</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<c:import url="/header"/>
<c:choose>
    <c:when test="${sessionScope.user.role.equals(UserType.ADMIN)}">
        <div class="container">
            <div class="row">
                <p class="col h1 mt-5">${nameCategories}</p>
                <c:if test="${not empty sessionScope.error}">
                    <div class="col alert alert-danger alert-dismissible" role="alert">
                            ${sessionScope.error}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"
                                onclick="${sessionScope.remove("error")}"></button>
                    </div>
                </c:if>
            </div>
            <table class="table table-hover">
                <tr>
                    <th>${nameCategoryName}</th>
                    <th>${nameBedsCount}</th>
                    <th>${nameRoomPrice}</th>
                    <th></th>
                    <th></th>
                </tr>
                <c:forEach items="${categories}" var="category">
                    <tr>
                        <td>${category.categoryName}</td>
                        <td>${category.bedsCount}</td>
                        <td>${category.roomPrice}</td>
                        <td>
                            <form method="get" action="#">
                                <input type="submit" class="btn btn-outline-dark" name="update-button" value="${btnChange}">
                                <input type="hidden" name="categoryName" value="${category.categoryName}">
                            </form>
                        </td>
                        <td>
                            <form method="post"
                                  action="${pageContext.request.contextPath}/controller?command=delete_category">
                                <input type="submit" class="btn btn-outline-dark" name="delete-button" value="${btnDelete}">
                                <input type="hidden" name="categoryName" value="${category.categoryName}">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <c:redirect url="${pageContext.request.contextPath}"/>
    </c:otherwise>
</c:choose>
<c:import url="/footer"/>
</body>
</html>
