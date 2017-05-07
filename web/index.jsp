<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>
<%@include file="Header.jsp"%>
<div class="container">
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
