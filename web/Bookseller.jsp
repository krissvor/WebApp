<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<%@include file="Header.jsp"%>
Add books
<form action="addBook"  METHOD="POST">

    <input type="hidden" name="action" value="addBook">
    <input type="text" name="title" placeholder="title">
    <input type="text" name="author" placeholder="author">
    <input type="text" name="publicationType" placeholder="Publication type">
    <input type="text" name="publicationDate" placeholder="Publication Date">
    <input type="text" name="venue" placeholder="venue">
    <input type="text" name="price" placeholder="price">
    <input type="text" name="venue" placeholder="venue">
    <input type="text" name="pages" placeholder="pages">
    <input type="text" name="url" placeholder="url">
    <input type="text" name="ee" placeholder="ee">
    <input type="file" name="picture" accept="image/*" placeholder="picture">
    <input type="submit">

</form>
</div>

</body>
</html>
