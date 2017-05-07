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
  <script src="Scripts/myScript.js"></script>
</head>
<body>
<div class="container">
  <nav class="navbar rounded navbar-toggleable-md navbar-inverse bg-inverse mb-4">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#containerNavbar" aria-controls="containerNavbar" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="containerNavbar">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="/">Digital Bibliographic Library <span class="sr-only">(current)</span></a>
        </li>


        <c:choose>
          <c:when test="${sessionScope.userId != null}">
            <li class="nav-item">
              <a class="nav-link" href="/cart">Shopping Cart</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/wishlist">Wishlist</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="/bookseller">Bookseller</a>
            </li>
            <li class="nav-item">
              <p ${sessionScope.name} </p>
            </li>
          </c:when>
            <c:otherwise>
              <li class="nav-item">
                <a class="nav-link" href="/register">User Registration</a>
              </li>
            </c:otherwise>
        </c:choose>
      </ul>
      <c:choose>
        <c:when test="${sessionScope.userId == null}">
          <ul class="navbar-nav pull-sm-right navbar-item">
            <li id="signInDropdown">

              <button class="btn btn-success" onclick="document.getElementById('id01').style.display='block'">Login</button>

              <div id="id01" class="modal">

                <form id="login-form" class="modal-content animate" action="login" METHOD="POST">
                  <input type="hidden" name="action" value="login">

                  <div class="container">
                    <label><b>Username</b></label>
                    <input id="username" type="text" placeholder="Enter Username" name="username" required>

                    <label><b>Password</b></label>
                    <input id="password" type="password" placeholder="Enter Password" name="password" required>
                    <button class="btn btn-success" type="button" onclick="validateLogin()">Login</button>
                    <input type="checkbox" checked="checked"/> Remember me
                  </div>

                  <div class="container" style="background-color:#f1f1f1">
                    <button class="btn btn-danger" type="button" onclick="document.getElementById('id01').style.display='none'">Cancel</button>
                    <span class="register"> <a href="UserRegistration">Register</a></span>
                  </div>
                </form>
              </div>

              <script>
                  // Get the modal
                  var modal = document.getElementById('id01');

                  // When the user clicks anywhere outside of the modal, close it
                  window.onclick = function(event) {
                      if (event.target == modal) {
                          modal.style.display = "none";
                      }
                  }
              </script>
            </li>
          </ul>
        </c:when>
        <c:otherwise>
          <ul class="navbar-nav pull-sm-right navbar-item">
            <form id="logout-form" action="logout" METHOD="POST">
              <input type="hidden" name="action" value="logout">
              <button type="submit" class="btn btn-danger">Log out</button>
            </form>
          </ul>
        </c:otherwise>
      </c:choose>
    </div>
  </nav>
