<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="by.urbel.hotel.entity.UserType" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.name.addingCategory" var="addingCategory"/>
<fmt:message bundle="${Loc}" key="Locale.name.categoryName" var="categoryName"/>
<fmt:message bundle="${Loc}" key="Locale.name.roomPrice" var="roomPrice"/>
<fmt:message bundle="${Loc}" key="Locale.name.bedsCount" var="bedsCount"/>
<fmt:message bundle="${Loc}" key="Locale.name.photoFiles" var="photoFiles"/>
<fmt:message bundle="${Loc}" key="Locale.button.add" var="add"/>

<html lang="${sessionScope.locale}">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${addingCategory}</title>
    <link rel="icon" href="${pageContext.request.contextPath}/img/hotel-icon.png">
    <%--    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css">--%>
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
                        ${addingCategory}
                </div>
            </div>
            <div class="d-flex justify-content-center mt-3">
                <form action="${pageContext.request.contextPath}/controller?command=add_new_category"
                      method="post" enctype="multipart/form-data">
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="categoryName">
                                ${categoryName}
                        </label>
                        <div class="col-12">
                            <input type="text" class="form-control" name="categoryName" id="categoryName" maxlength="45"
                                   required>
                        </div>
                    </div>
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="roomPrice">
                                ${roomPrice}
                        </label>
                        <div class="col-12">
                            <input type="number" class="form-control" name="roomPrice" id="roomPrice" min="0"
                                   max="100000" required>
                        </div>
                    </div>
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="bedsCount">
                                ${bedsCount}
                        </label>
                        <div class="col-12">
                            <input type="number" class="form-control" name="bedsCount" id="bedsCount" min="0" max="4"
                                   required>
                        </div>
                    </div>
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="categoryPhotos">
                                ${photoFiles}
                        </label>
                        <div class="col-12">
                            <input type="file" class="form-control" name="categoryPhotos" id="categoryPhotos"
                                   multiple accept="image/*" max="3" required>
                        </div>
                    </div>
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="description_ru">
                            DESCRIPTION ON RU
                        </label>
                        <div class="col-12">
                            <textarea name="description_ru" id="description_ru" maxlength="200"
                                      rows="5" class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="row m-2">
                        <label class="col-12 m-2 form-label" for="description_en">
                            DESCRIPTION ON EN
                        </label>
                        <div class="col-12">
                            <textarea name="description_en" id="description_en" maxlength="200"
                                      rows="5" class="form-control"></textarea>
                        </div>
                    </div>
                    <div class="row m-2 mt-4">
                        <div class="col-12">
                            <input type="submit" class="form-control" value="${add}">
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
