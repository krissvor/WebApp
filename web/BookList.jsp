<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
  <title>Booklist</title>
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
<div class="container">
    <h1>Results</h1>
  </div>
  <table class="table">
    <c:forEach items="${BookBeans}" var="bookBean">
      <tr>
        <td><c:out value="${bookBean.getTitle()}" /></td>
        <td><c:out value="${bookBean.getAuthor()}" /></td>
        <td><c:out value="${bookBean.getPublicationType()}" /></td>
        <td><c:out value="${bookBean.getPublicationDate()}" /></td>
        <td><c:out value="${bookBean.getVenues()}" /></td>
        <form action="/search" method="post">
          <input type="hidden" value="${bookBean.getId()}">
          <td><input type="submit" class="btn btn-secondary btn-md" value="Add to cart"></td>
        </form>
      </tr>
    </c:forEach>
  </table>
  <%
    // Generate pagination links
    int currentPage = Integer.valueOf(request.getAttribute("page").toString());
    String term = request.getAttribute("term").toString();
    String attribute = request.getAttribute("attribute").toString();

    // We don't want a prev page link if current page is 0
    String prevPageLink = (currentPage > 0) ? "http://localhost:8081/search?term=" + term + "&attribute=" + attribute + "&page=" + (currentPage - 1) : null;

    String nextPageLink = "http://localhost:8081/search?term=" + term + "&attribute=" + attribute + "&page=" + (currentPage + 1);

    request.setAttribute("nextPageLink", nextPageLink);
    request.setAttribute("prevPageLink", prevPageLink);
  %>
  <c:choose>
    <c:when test="${BookBeans == null || BookBeans.size() == 0}">
      <h3 class="text-center"> Your search matched no publications. </h3>
    </c:when>
    <c:otherwise>
      <c:if test="${prevPageLink != null}">
        <a class="card-link" href=${prevPageLink}> Previous </a>
      </c:if>
      <c:if test="${nextPageLink != null}">
        <a class="card-link" href=${nextPageLink}> Next </a>
      </c:if>
    </c:otherwise>
  </c:choose>
</div>
</body>
</html>
