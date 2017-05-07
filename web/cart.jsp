<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
    <body>
        <%@include file="Header.jsp"%>
        <div class="container">
            <h1 class="text-center">Shopping Cart</h1>
            <div class="form-group">
                <form action="/purchase" onsubmit="alert('Thanks for your purchase!')" method="post">
                    <input type="hidden" value="purchase" name="action">
                </form>
            </div>
            <table class="table">
                <c:choose>
                    <c:when test="${cartBooks == null || cartBooks.size() == 0}">
                        <h3 class="text-center"> There are no books in the shopping cart </h3>
                        <h3 class="text-center"> <a href="/"> Find some books to add them to the cart. </a> </h3>
                    </c:when>
                    <c:otherwise>
                        <button type="submit" class="btn btn-secondary btn-md" value="purchase">Purchase</button>

                        <c:forEach items="${cartBooks}" var="bookBean">
                            <tr>
                                <td><c:out value="${bookBean.getTitle()}" /></td>
                                <td><c:forEach items="${bookBean.getAuthor()}" var="author"><c:out value="${author}" />,
                                    </br></c:forEach></td>
                                <td><c:out value="${bookBean.getPublicationType()}" /></td>
                                <td><c:out value="${bookBean.getPublicationDate()}" /></td>
                                <td><c:out value="${bookBean.getVenues()}" /></td>
                                <td>
                                    <div class="form-group">
                                        <form action="/cart" method="post">
                                            <input type="hidden" name="id" value=${bookBean.id}>
                                            <input type="hidden" name="action" value="remove">
                                            <button class="btn btn-primary" type="submit"> Remove from cart </button>
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
