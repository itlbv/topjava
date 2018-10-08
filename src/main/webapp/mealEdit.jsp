<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактировать</title>
</head>
<body>
    <form method="POST">
        <table>
            <tr>
                <td>Дата и время:</td>
                <td><input type="datetime-local" name="dateTime" value="${meal.dateTime}"/></td>
            </tr>
            <tr>
                <td>Описание:</td>
                <td><input type="text" name="description" value="${meal.description}"/></td>
            </tr>
            <tr>
                <td>Калории:</td>
                <td><input type="text" name="calories" value="${meal.calories}"/></td>
            </tr>
            <tr>
                <td><input type="submit" value="Сохранить" /></td>
                <td><input type="hidden" name="id" value="${id}" /></td>
            </tr>
        </table>
    </form>
</body>
</html>
