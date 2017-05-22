<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<%@include file="Header.jsp"%>
<div class="container">
    <h1>Add books</h1>
    <form action="addBook"  METHOD="POST" onsubmit="return validateBookForm()">
    <div class="form-horizontal">
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="title" id="title" class="necessary form-control UserRegInput" placeholder="Title">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="author" id="author" class="necessary form-control UserRegInput" placeholder="author, separate by ','">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="publicationType"id="publicationType" class="necessary form-control UserRegInput" placeholder="Publication type">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="date" name="publicationDate" id="publicationDate" class="necessary form-control UserRegInput" placeholder="Publication Date">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="venue" id="venue"class="necessary form-control UserRegInput" placeholder="Venue">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="price" id="price" class="necessary form-control UserRegInput" placeholder="Price">
            </div>
        </div>
        <div class="form-group">
            <div class="col-lg-6">
                <input type="text" name="pages" id="pages" class="form-control UserRegInput" placeholder="Pages">
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
</div>
</body>
</html>
