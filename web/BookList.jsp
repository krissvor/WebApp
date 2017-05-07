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
<body>
<%@include file="Header.jsp"%>
<div class="container">
  <h1>Results</h1>
  <table class="table">
    <c:forEach items="${BookBeans}" var="bookBean">
      <tr>
        <td><c:out value="${bookBean.getTitle()}" /></td>
        <td>
          <p class="card-text no-margin">
            <c:forEach items="${bookBean.author}" var="authorElem">
              ${authorElem},
            </c:forEach>
          </p>
        </td>
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
