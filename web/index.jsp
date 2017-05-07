<%--
  Created by IntelliJ IDEA.
  User: kriss
  Date: 01-May-17
  Time: 2:30 PM
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
  <script src="Scripts/myScript.js"></script>
</head>
<body>
<%@include file="Header.jsp"%>
<div class="container">
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
