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
    <title>Home</title>
    <meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
    <script src="https://use.fontawesome.com/fe48c1e1ca.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="Styles.css">
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
</head>
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
