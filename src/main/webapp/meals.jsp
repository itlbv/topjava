<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>


<style>
tr.green td{color: darkgreen;}
tr.red td{color: coral;}
</style>

<table>
    <th>Дата</th>
    <th>Описание</th>
    <th>Калории</th>
    <c:forEach var="meal" items="${meals}">
        <c:set var="className" value="${meal.isExceed() ? 'red' : 'green'}"/>
        <tr  class=${className}>
            <td>${localDateTimeFormat.format(meal.getDateTime())}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
</table>

</body>
</html>