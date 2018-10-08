<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Редактировать</title>
</head>
<body>
    <form method="POST" action='meals' name="frmAddUser">
        Дата и время : <input type="datetime-local" name="dateTime" data-date-format
                           value="${meal.dateTime}" /> <br />
        Описание : <input type="text" name="description"
                             value="<c:out value="${meal.description}" />" /> <br />
        Калории : <input type="text" name="calories"
                          value="<c:out value="${meal.calories}" />" /> <br />
        <input type="submit" value="Сохранить" />

        <input type="hidden" name="id" value="${id}" /> <br />
    </form>
</body>
</html>
