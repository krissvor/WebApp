<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<%@include file="Header.jsp"%>
<form action="UserRegistration" data-toggle="validator" METHOD="POST">
    <input type="hidden" name = "action" value = "UserRegistration">
    <div class="form-horizontal">
        <h1>User Registration</h1>
        <div class="form-group" >
            <label for="firstName" class="col-sm-2 control-label">First Name</label>
            <div class="col-lg-6">
                <input id = firstname" name="firstname" type="firstName" class="form-control UserRegInput" id="firstName" placeholder="First Name">
            </div>
        </div>
        <div class="form-group">
            <label for="lastname" class="col-sm-2 control-label">Last Name</label>
            <div class="col-lg-6">
                <input type="lastname" name="lastname"class="form-control UserRegInput" id="lastname" placeholder="Last Name">
            </div>
        </div>
        <div class="form-group" >
            <label for="year" class="col-sm-2 control-label">Year of birth</label>
            <div class="col-lg-6">
                <input type="year" name="year" class="form-control UserRegInput" id="year" placeholder="Year">
            </div>
        </div>
        <div class="form-group" >
            <label for="username" class="col-sm-2 control-label">Username</label>
            <div class="col-lg-6">
                <input type="username" name="username" class="form-control UserRegInput" id="username" placeholder="Username">
            </div>
        </div>
        <div class="form-group" >
            <label for="nickname" class="col-sm-2 control-label">Nickname</label>
            <div class="col-lg-6">
                <input type="nickname" name="nickname" class="form-control UserRegInput" id="nickname" placeholder="Nickname">
            </div>
        </div>
        <div class="form-group" >
            <label for="email" class="col-sm-2 control-label">Email</label>
            <div class="col-lg-6">
                <input type="email" name="email"class="form-control UserRegInput" id="email" placeholder="Email">
            </div>
        </div>
        <div class="form-group">
            <label for="password1" class="col-sm-2 control-label">Password</label>
            <div class="col-lg-6">
                <input type="password" name="password"class="form-control UserRegInput" id="password1" placeholder="********">
            </div>
        </div>
        <div class="form-group">
            <label for="password2" class="col-sm-2 control-label">Password</label>
            <div class="col-lg-6">
                <input type="password" data-match="#password1" data-match-error="Whoops, these don't match" class="form-control UserRegInput" id="password2" placeholder="********">
            </div>
        </div>
        <div class="form-group" >
            <label for="creditCard" class="col-sm-2 control-label">Creditcard Number</label>
            <div class="col-lg-6">
                <input type="creditCard" name="creditcard"class="form-control UserRegInput" id="creditCard" placeholder="1234 5678 9101 1121">
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
