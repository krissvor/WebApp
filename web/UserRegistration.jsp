<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<%@include file="Header.jsp"%>
<h1>User Registration</h1>
<form action="/register" data-toggle="validator" onsubmit="return testUserForm()" METHOD="POST">

    <input type="hidden" name = "action" value = "UserRegistration">
    <div class="form-horizontal">
        <div class="form-group" >
            <div class="col-lg-6">
                <input type="text" id = firstname" name="firstname" type="firstName" class="form-control UserRegInput" id="firstName" placeholder="First Name">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="lastname" class="form-control UserRegInput" id="lastname" placeholder="Last Name">
            </div>
        </div>
        <div class="form-group" >
            <div class="col-lg-6">
                <input type="text" name="year" class="form-control UserRegInput" id="year" placeholder="Year">
            </div>
        </div>
        <div class="form-group" >
            <div class="col-lg-6">
                <input type="text" name="username" class="form-control UserRegInput" onfocusout="validateUsername()" id="username" placeholder="Username">
            </div>
        </div>
        <div class="form-group" >
            <div class="col-lg-6">
                <input type="text" name="nickname" class="form-control UserRegInput" id="nickname" placeholder="Nickname">
            </div>
        </div>
        <div class="form-group" >
            <div class="col-lg-6">
                <input type="text" name="email"class="form-control UserRegInput" id="email" placeholder="Email">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="password" name="password"class="form-control UserRegInput" id="password1" placeholder="Password">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="password" data-match="#password1" data-match-error="Whoops, these don't match" class="form-control UserRegInput" id="password2" placeholder="Repeat password">
            </div>
        </div>
        <div class="form-group" >
            <div class="col-lg-6">
                <input type="text" name="creditcard"class="form-control UserRegInput" id="creditCard" placeholder="Credit card number">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-lg-6">
                <button type="submit" class="btn btn-secondary">Sign in</button>
            </div>
        </div>
    </div>

</form>

</body>
</html>
