<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <body>
        <%@include file="Header.jsp"%>
        <div class="container">
            <h1 class="text-center">Shopping Cart</h1>
            <table class="table">
                <c:choose>
                    <c:when test="${BookBeans == null || BookBeans.size() == 0}">
                        <h3 class="text-center"> There are no books in the shopping cart </h3>
                        <h3 class="text-center"> <a href="/"> Find some books to add them to the cart. </a> </h3>
                    </c:when>
                    <c:otherwise>
                        <c:forEach items="${cartBooks}" var="bookBean">
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
                    </c:otherwise>
                </c:choose>
            </table>
        </div>
    </body>
</html>
