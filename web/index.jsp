<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<%@include file="Header.jsp"%>
<div class="container">
  <nav class="navbar rounded navbar-toggleable-md navbar-inverse bg-inverse mb-4">
    <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#containerNavbar" aria-controls="containerNavbar" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="containerNavbar">
      <%--<ul class="nav navbar-nav pull-sm-right" id="signInDropdown">--%>
        <%--<li class="dropdown">--%>
        <%--<button type="button" id="dropdownMenu1" data-toggle="dropdown" class="btn btn-secondary dropdown-toggle"><span><i class="fa fa-user" aria-hidden="true"></i></span>  Login <span class="caret"></span></button>--%>
        <%--<ul class="dropdown-menu">--%>
        <%--<li class="dropdown-item">--%>
        <%--<form class="form">--%>
        <%--<div class="form-group">--%>
        <%--<input name="username" placeholder="Username" class="form-control form-control-sm" type="text" required="">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
        <%--<input name="password" placeholder="Password" class="form-control form-control-sm" type="password" required="">--%>
        <%--</div>--%>
        <%--<div class="form-group">--%>
        <%--<button type="submit" class="btn btn-primary btn-block">Login</button>--%>
        <%--</div>--%>
        <%--<div class="form-group text-xs-center">--%>
        <%--<small><a href="UserRegistration.jsp">Don't have a user? Register here</a></small>--%>
        <%--</div>--%>
        <%--</form>--%>
        <%--</li>--%>
        <%--</ul>--%>
        <%--</li>--%>
        <%--</ul>--%>

      <ul class="navbar-nav mr-auto">
        <li class="nav-item active">
          <a class="nav-link" href="#">Digital Bibliographic Library <span class="sr-only">(current)</span></a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="UserRegistration.jsp">User Registration</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="Bookseller.jsp">Bookseller</a>
        </li>
      </ul>
        <ul class="nav navbar-nav pull-sm-right" id="signInDropdown">

          <button onclick="document.getElementById('id01').style.display='block'" style="width:auto;">Login</button>


          <div id="id01" class="modal">

            <form class="modal-content animate" action="login" METHOD="POST">
              <input type="hidden" name="action" value="login">

              <div class="container">
                <label><b>Username</b></label>
                <input type="text"  id="username" placeholder="Enter Username" name="username" required>

                <label><b>Password</b></label>
                <input type="password" placeholder="Enter Password" name="password" required>

                <button type="submit">Login</button>
                <input type="checkbox" checked="checked"> Remember me
              </div>

              <div class="container" style="background-color:#f1f1f1">
                <button type="button" onclick="document.getElementById('id01').style.display='none'" class="cancelbtn">Cancel</button>
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

        </ul>
    </div>
  </nav>
>>>>>>> master
  <!--<div class="jumbotron">
    <h1 class="display-3">Digital Bibliographic Library</h1>
  </div>-->
  <div class="imagediv">
  <img src="books.jpg" class="img-fluid" alt="Responsive image">
  </div>
  <div class="">
    <form action="/search" method="GET">
      <div class="input-group">
        <div class="input-group-btn">
          <button type="button" class="btn btn-secondary dropdown-toggle" style="margin-bottom:0px" name="selectSearch" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Search By: <span class="selection" name="attribute">Title</span><span class="caret"></span>
          </button>
          <ul class="dropdown-menu">
            <li><a class="dropdown-item" data-value="author" href="#">Author</a></li>
            <li><a class="dropdown-item" data-value="title" href="#">Title</a></li>
            <li><a class="dropdown-item" data-value="year" href="#">Year</a></li>
            <li><a class="dropdown-item" data-value="venue" href="#">Venue</a></li>
          </ul>
        </div>
        <input type="text" class="form-control" name="term" placeholder="Seach term...">
        <span class="input-group-btn">
                <button type="submit" style="margin-bottom:0px" class="btn btn-secondary"><i class="fa fa-search" aria-hidden="true"></i></button>
            </span>
        <input type="hidden" class="hiddenInput" name="attribute" value="Title">
        <input type="hidden" name="page" value="0">
      </div>
    </form>
  </div>
  <h3 class="text-center"> Random books </h3>
  <div>
    <table class="table">
      <c:forEach items="${randomBooks}" var="bookBean">
        <tr>
          <td><c:out value="${bookBean.getTitle()}" /></td>
          <td><c:out value="${bookBean.getAuthor()}" /></td>
          <td><c:out value="${bookBean.getPublicationType()}" /></td>
          <td><c:out value="${bookBean.getPublicationDate()}" /></td>
          <td><c:out value="${bookBean.getVenues()}" /></td>
          <form action="/book" method="get">
            <input type="hidden" name="id" value=${bookBean.id}>
            <td><input type="submit" class="btn btn-secondary btn-md" value="Details"></td>
          </form>
        </tr>
      </c:forEach>
    </table>
  </div>
</div>
<script type="text/javascript">
  $(".dropdown-menu li a").click(function(){
    $(this).parents(".input-group-btn").find('.selection').text($(this).text());
    $(this).parents(".input-group-btn").find('.selection').val($(this).text());
    $(this).parents(".input-group").find('.hiddenInput').val($(this).text());

  });
</script>

<a href="Admin.jsp">ADMIIIIIN</a>
</body>
</html>
