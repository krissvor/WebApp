<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
    <%@include file="Header.jsp"%>
    <div class="container">
        <h4 class="card-title text-center top-bot-20">${bookBean.title}</h4>
        <div class="d-flex justify-content-center">
            <table class="table limit-width">
                <tbody>
                <tr>
                    <td> <p class="right-margin-10 no-margin text-muted">Publication type:</p></td>
                    <td> <p class="no-margin">${bookBean.publicationType}</p></td>
                </tr>
                <tr>
                    <td>  <p class="right-margin-10 no-margin text-muted">Publisher:</p></td>
                    <td> <p class="no-margin">${bookBean.venues}</p></td>
                </tr>
                <tr>
                    <td> <p class="right-margin-10 no-margin text-muted">Year published:</p></td>
                    <td> <p class="no-margin">${bookBean.publicationDate}</p></td>
                </tr>
                <tr>
                    <td> <p class="right-margin-10 no-margin text-muted">Authors: </p></td>
                    <td>
                        <p class="card-text no-margin">
                            <c:forEach items="${bookBean.author}" var="authorElem">
                                ${authorElem},
                            </c:forEach>
                        </p>
                    </td>
                </tr>
                <tr>
                    <td> <p class="right-margin-10 no-margin text-muted">EE: </p> </td>
                    <td> <a href="${bookBean.url}" class="card-link">${bookBean.url}</a></td>
                </tr>
                <tr>
                    <td> <p class="right-margin-10 no-margin text-muted">Price:</p></td>
                    <td> <p class="no-margin">${bookBean.price}</p></td>
                </tr>
                <tr>
                    <td> <p class="right-margin-10 no-margin text-muted">Picture:</p></td>
                    <td> <p class="no-margin">${bookBean.picture}</p></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="row d-flex justify-content-around">
            <c:choose>
                <c:when test="${sessionScope.bookIds.contains(bookBean.id)}">
                    <div class="form-group">
                        <form action="/cart" method="post">
                            <input type="hidden" name="id" value=${bookBean.id}>
                            <input type="hidden" name="action" value="remove">
                            <button class="btn btn-primary" type="submit"> Remove from cart </button>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <form action="/cart" method="post">
                            <input type="hidden" name="id" value=${bookBean.id}>
                            <input type="hidden" name="action" value="add">
                            <button class="btn btn-primary" type="submit"> Add to cart </button>
                        </form>
                    </div>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${sessionScope.userId != null}">
                    <div class="form-group">
                        <form action="/wishlist" method="post">
                            <input type="hidden" name="id" value=${bookBean.id}>
                            <input type="hidden" name="action" value="add">
                            <button class="btn btn-primary" type="submit"> Add to wish list</button>
                        </form>
                    </div>
                </c:when>
            </c:choose>
        </div>
        <div>
            <form method="POST" action="addReview">
            <textarea rows="6" cols="60" id="inputReview" placeholder="Have you read this book? leave a review!"></textarea>
                <button class="btn btn-primary" type="submit">Leave review</button>
            </form>
            <button class="btn btn-primary" onclick="extractReview()">Extract info</button>


        </div>
    </div>
</body>
</html>
