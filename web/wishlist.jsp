<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<%@include file="Header.jsp"%>
<div class="container">
    <h1 class="text-center">Wish list</h1>
    <table class="table">
        <c:choose>
            <c:when test="${wishedBooks == null || wishedBooks.size() == 0}">
                <h3 class="text-center"> There are no books in the wish list </h3>
                <h3 class="text-center"> <a href="/"> Find some books to add them to the wish list. </a> </h3>
            </c:when>
            <c:otherwise>
                <c:forEach items="${wishedBooks}" var="bookBean">
                    <td>
                        <td><c:out value="${bookBean.getTitle()}" /></td>
                        <td><c:forEach items="${bookBean.getAuthor()}" var="author"><c:out value="${author}" />,
                        </br></c:forEach></td>
                        <td><c:out value="${bookBean.getPublicationType()}" /></td>
                        <td><c:out value="${bookBean.getPublicationDate()}" /></td>
                        <td><c:out value="${bookBean.getVenues()}" /></td>
                        <td>
                            <div class="form-group">
                                <form action="/wishlist" method="post">
                                    <input type="hidden" name="id" value=${bookBean.id}>
                                    <input type="hidden" name="action" value="remove">
                                    <button class="btn btn-primary" type="submit"> Remove from wish list </button>
                                </form>
                            </div>
                        </td>
                        <td>
                            <div class="form-group">
                                <form action="/book" method="get">
                                    <input type="hidden" name="id" value=${bookBean.id}>
                                    <input type="submit" class="btn btn-secondary btn-md" value="Details">
                                </form>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </table>
</div>
</body>
</html>
