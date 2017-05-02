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

Add booke
<form action="addBook" METHOD="POST">
    
    <input type="hidden" name="action" value="addBook">
    <input type="text" name="title" placeholder="title">
    <input type="text" name="author" placeholder="author">
    <input type="submit" >

</form>

</body>
</html>
