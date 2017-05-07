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

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("In login...");
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();
        HttpSession session = request.getSession();

        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        UserBean user = sqlHandler.verifyPassword(username, password);
        sqlHandler.closeConnection();

        if(user != null) {
            System.out.println("Found user!: " +  user);
            session.setAttribute("userId", user.getId());
            session.setAttribute("name", user.getFirstName() + " " + user.getLastName());
            session.setAttribute("username", user.getUsername());
            response.sendRedirect("/");
        } else {
            System.out.println("User not found!");
            response.getWriter().write("false");
        }
    }

    public void checkLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        SqlHandler handler = new SqlHandler();
        handler.connect();
        response.setContentType("text/html");
        UserBean user = handler.verifyPassword(username, password);
        if(user !=null){
            response.getWriter().write("true");
        }else {
            response.getWriter().write("false");
        }
        handler.closeConnection();
    }
}
