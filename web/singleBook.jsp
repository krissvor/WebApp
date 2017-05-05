<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
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
            <div class="form-group">
                <form action="/cart" method="post">
                    <input type="hidden" name="id" value=${bookBean.id}>
                    <button class="btn btn-secondary" type="submit"> Add to cart </button>
                </form>
            </div>
            <div class="form-group">
                <form action="/wishlist" method="post">
                    <input type="hidden" name="id" value=${bookBean.id}>
                    <button class="btn btn-secondary" type="submit"> Add to wish list</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
