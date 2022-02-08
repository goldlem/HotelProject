<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="locale" var="Loc"/>
<fmt:message bundle="${Loc}" key="Locale.creator.designed" var="creatorDesigned"/>
<fmt:message bundle="${Loc}" key="Locale.name.designedBy" var="designedBy"/>

<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Footer</title>
</head>
<body>
<footer class="container-fluid footer bg-dark text-light" style="margin-top: 150px">
    <div class="row p-5">
        <div class="col-md-6">
            Copyright ©
            <script>document.write(new Date().getFullYear().toString());</script>
            | ${designedBy}
            <a class="text-light" href="https://vk.com/gold1em">
                ${creatorDesigned}
            </a>
        </div>
    </div>
</footer>
</body>
</html>
