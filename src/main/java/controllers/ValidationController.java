package controllers;

import Beans.UserBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Hanna on 07.05.2017.
 */
public class ValidationController {

    public void confirmUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        UserBean user = sqlHandler.findUserById(userId);
        System.out.println("fant bruker: " + user);
        user.setIs_active(true);
        sqlHandler.updateUser(user);
        sqlHandler.closeConnection();
        request.getRequestDispatcher("confirmation.jsp").forward(request, response);

    }
}
