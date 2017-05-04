<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Booklist</title>
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
<div class="container">
    <h1>Users</h1>
  </div>
  <table class="table">
    <c:forEach items="${users}" var="userBean">
      <tr>
        <td><c:out value="${userBean.getFirstName()}" /></td>
        <td><c:out value="${userBean.getLastName()}" /></td>
        <td><c:out value="${userBean.getBirthYear()}" /></td>
        <td><c:out value="${userBean.getUsername()}" /></td>
        <td><c:out value="${userBean.getNickname()}" /></td>
        <form action="/deleteUser" method="post">
          <input type="hidden" name="id" value="${userBean.getId()}">
          <input type="hidden" name="action" value="deleteUser">
          <td><input type="submit" class="btn btn-secondary btn-md" value="Delete user"></td>
        </form>
      </tr>
    </c:forEach>
  </table>
</div>
</body>
</html>
