<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Импорт данных из файла</title>
</head>
<body>
Импортируем пользователей:
<form action="./createPage">
<p>
Путь к файлу:<input name="filePath" value="<%= request.getAttribute("filePath") %>">
<p>
<input type="radio" name="update" value="1" checked> Обновлять записи<br>
<input type="radio" name="update" value="2"> Пропустить записи, если таковые уже имеются<br>
<p>
<input type="submit" name="pageType" value="Import">
<input type="submit" name="pageType" value="Back to menu">

</body>
</html>