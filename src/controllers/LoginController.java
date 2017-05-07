package controllers;

import Beans.UserBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by kriss on 03-May-17.
 */
public class LoginController {

    public LoginController(){

    }

    public UserBean login(HttpServletRequest request) throws ServletException, IOException {
        System.out.println("In login...");
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        UserBean user = sqlHandler.verifyPassword(username, password);
        sqlHandler.closeConnection();

        if(user != null) {
            System.out.println("Found user!: " +  user);
            return user;

        } else {
            System.out.println("User not found!");
            return null;
        }
    }

    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        SqlHandler handler = new SqlHandler();
        handler.connect();
        UserBean user = handler.verifyPassword(username, password);
        if(user !=null){
            response.getWriter().write("true");
        }else {
            response.getWriter().write("false");
        }
        handler.closeConnection();
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("userId", null);
        session.setAttribute("name", null);
        session.setAttribute("username", null);
        session.invalidate();
        response.sendRedirect("/");
    }
}
