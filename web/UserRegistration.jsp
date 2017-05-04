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
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.1.1.slim.min.js" integrity="sha384-A7FZj7v+d/sdmMqp/nOQwliLvUsJfDHW+k9Omg/a/EheAdgtzNs3hpfag6Ed950n" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
    <title>Title</title>
</head>
<body>

<form action="UserRegistration" data-toggle="validator" onsubmit="return testUserForm()" METHOD="POST">
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
                <input type="username" name="username" class="form-control UserRegInput" onfocusout="validateUsername()" id="username" placeholder="Username">
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
