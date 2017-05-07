<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <body>
        <%@include file="Header.jsp"%>
        <div class="container">
            <h2 class="text-center"> Your books for sale </h2>
            <div>
                <table class="table">
                    <c:forEach items="${books}" var="bookBean">
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
                    <c:choose>
                        <c:when test="${books == null || books.size() == 0}">
                            <h4 class="text-center"> You have no books for sale. </h4>
                            <h4 class="text-center"> Would you like to <a href="/bookseller"> add</a> some? </h4>
                        </c:when>
                    </c:choose>
                </table>
            </div>
        </div>

    </body>
</html>
