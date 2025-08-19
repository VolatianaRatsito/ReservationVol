<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Résultat de la réservation</title>
</head>
<body>

<h2>Résultat de la réservation</h2>

<c:choose>
    <c:when test="${success}">
        <p style="color:green;">${message}</p>
    </c:when>
    <c:otherwise>
        <p style="color:red;">${message}</p>
    </c:otherwise>
</c:choose>

<p><a href="${pageContext.request.contextPath}/add">Faire une nouvelle réservation</a></p>
<p><a href="${pageContext.request.contextPath}/reservations?idPassager=${param.idPassager}">Voir mes réservations</a></p>

</body>
</html>
