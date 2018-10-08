<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<style>
    tr.green td{color: darkgreen}
    tr.red td{color: red}
</style>

<p><a href="meals?action=insert">Добавить</a></p>

<table>
    <th>Дата</th>
    <th>Описание</th>
    <th>Калории</th>
    <c:forEach var="meal" items="${meals}">
        <c:set var="className" value="${meal.exceed ? 'red' : 'green'}"/>
        <tr class=${className}>
            <td>${dateTimeFormatter.format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&mealId=<c:out value="${meal.id}"/>">Изменить</a></td>
            <td><a href="meals?action=delete&mealId=<c:out value="${meal.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
</table>

</body>
</html>