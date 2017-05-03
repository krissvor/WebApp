<%--
  Created by IntelliJ IDEA.
  User: kriss
  Date: 01-May-17
  Time: 6:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
Bookseller

Add books
<form action="addBook"  METHOD="POST">

    <input type="hidden" name="action" value="addBook">
    <input type="text" name="title" placeholder="title">
    <input type="text" name="author" placeholder="author">
    <input type="text" name="publicationType" placeholder="Publication type">
    <input type="text" name="publicationDate" placeholder="Publication Date">
    <input type="text" name="venue" placeholder="venue">
    <input type="text" name="price" placeholder="price">
    <input type="file" name="picture" accept="image/*" placeholder="picture">
    <input type="submit" >

</form>

</body>
</html>
