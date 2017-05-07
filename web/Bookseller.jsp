<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<%@include file="Header.jsp"%>
<h1>Add books</h1>
<form action="addBook"  METHOD="POST">
    <div class="form-horizontal">
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="title" class="form-control UserRegInput" placeholder="Title">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="author" class="form-control UserRegInput" placeholder="author">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="publicationType" class="form-control UserRegInput" placeholder="Publication type">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="publicationDate" class="form-control UserRegInput" placeholder="Publication Date">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="venue" class="form-control UserRegInput" placeholder="Venue">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="price" class="form-control UserRegInput" placeholder="Price">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="pages" class="form-control UserRegInput" placeholder="Pages">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="url" class="form-control UserRegInput" placeholder="url">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="ee" class="form-control UserRegInput" placeholder="ee">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-lg-6">
                <input type="file" name="picture" accept="image/*" class="form-control UserRegInput" placeholder="picture">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-lg-6">
                <button type="submit" class="btn btn-secondary">Submit</button>
            </div>
        </div>
    </div>
</form>
</body>
</html>
