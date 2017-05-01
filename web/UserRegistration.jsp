<%--
  Created by IntelliJ IDEA.
  User: kriss
  Date: 01-May-17
  Time: 2:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script type="text/javascript" src="Scripts/myScript.js"></script>
    <title>Title</title>
</head>
<body>
User Registration

<form action="UserRegistration" onsubmit="return testUserForm(this);" METHOD="POST">
    <input type="hidden" name="action" value="UserRegistration">

    First Name: <input type="text" name="firstname" placeholder="First name"></br>
    Last Name: <input type="text" name="lastname" placeholder="Last name"></br>
    Year of birth: <input id="year" type="text" name="Birthyear"></br>
    Username: <input type="text" name="username" placeholder="Username"></br>
    Nickname: <input type="text" name="nickname" placeholder="Nickname"></br>
    Email: <input id = "email" type="text" name="email" placeholder="name@example.com"></br>
    Password: <input id="password" type="password" name="password" placeholder="********"></br>
    Credit card number: <input type="text" name="credit" placeholder="1234 5678 9101 1121"></br>
    <input type="submit">
</form>
<button onClick="testUserForm()">Test skjemaet</button>

</body>
</html>
