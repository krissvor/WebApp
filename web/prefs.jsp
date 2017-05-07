<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <body>
        <%@include file="Header.jsp"%>
        <div class="container">
            <h1 class="text-center"> Update user </h1>
            <form action="prefs" data-toggle="validator" onsubmit="return testUpdateForm()" method="post">
                <input type="hidden" name="action" value="updateUser">
                <div class="form-horizontal">
                    <div class="form-group" >
                        <div class="col-lg-6">
                            <input type="text" id = firstname" name="firstname" type="firstName" class="form-control UserRegInput" id="firstName" placeholder="First Name" value="${user.firstName}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-6">
                            <input type="text" name="lastname" class="form-control UserRegInput" id="lastname" placeholder="Last Name" value="${user.lastName}">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-6">
                            <input type="text" name="year" class="form-control UserRegInput" id="year" placeholder="Year" value="${user.birthYear}">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-6">
                            <input type="text" name="nickname" class="form-control UserRegInput" id="nickname" placeholder="Nickname" value="${user.nickname}">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-6">
                            <input type="text" name="email"class="form-control UserRegInput" id="email" placeholder="Email" value="${user.email}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-6">
                            <input type="password" name="password"class="form-control UserRegInput" id="password1" placeholder="Password" value="${user.password}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-6">
                            <input type="password" data-match="#password1" data-match-error="Whoops, these don't match" class="form-control UserRegInput" id="password2" placeholder="Repeat password" value="${user.password}">
                        </div>
                    </div>
                    <div class="form-group" >
                        <div class="col-lg-6">
                            <input type="text" name="creditcard"class="form-control UserRegInput" id="creditCard" placeholder="Credit card number" value="${user.creditCard}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-lg-6">
                            <input type="text" name="address" class="form-control UserRegInput" id="address" placeholder="Address" value="${user.address}">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-lg-6">
                            <button type="submit" class="btn btn-secondary">Update your information</button>
                        </div>
                    </div>
                </div>
            </form>

            <h2 class="text-center"> Your books for sale </h2>
            <div>
                <table class="table">
                    <c:forEach items="${books}" var="bookBean">
                        <tr>
                            <td><c:out value="${bookBean.getTitle()}" /></td>
                            <td>
                                <c:forEach items="${bookBean.author}" var="authorElem">
                                    ${authorElem},
                                </c:forEach>
                            </td>
                            <td><c:out value="${bookBean.getPublicationType()}" /></td>
                            <td><c:out value="${bookBean.getPublicationDate()}" /></td>
                            <td><c:out value="${bookBean.getVenues()}" /></td>
                            <form action="/book" method="get">
                                <input type="hidden" name="id" value=${bookBean.id}>
                                <td><input type="submit" class="btn btn-secondary btn-md" value="Details"></td>
                            </form>
                            <form action="/prefs" method="post">
                                <input type="hidden" name="id" value=${bookBean.id}>
                                <input type="hidden" name="action" value="remove">
                                <td><input type="submit" class="btn btn-danger btn-md" value="Remove from sale"></td>
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
