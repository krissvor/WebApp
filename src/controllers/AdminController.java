package controllers;

import Beans.UserBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Hanna on 07.05.2017.
 */
public class AdminController {

    public void adminLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LoginController login = new LoginController();
        UserBean admin = login.adminLogin(request);
        if(admin != null){
            HttpSession session = request.getSession();
            session.setAttribute("isAdmin", true);
            response.sendRedirect("/adminpage");

        } else {
            response.sendRedirect("admin.jsp");
        }



    }

    public void showLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("admin.jsp").forward(request, response);
    }

    public void showAdminPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        System.out.println("IS admin: " + (Boolean)session.getAttribute("isAdmin"));
        if((Boolean) session.getAttribute("isAdmin")) {
            SqlHandler sqlHandler = new SqlHandler();
            sqlHandler.connect();
            ArrayList<UserBean> users = new ArrayList<>();
            users = sqlHandler.getAllUsers();
            request.setAttribute("users", users);
            //int id = Integer.parseInt(request.getParameter("id"));
            //sqlHandler.deleteUser(id);
            sqlHandler.closeConnection();
            request.getRequestDispatcher("adminpage.jsp").forward(request, response);
        }
        else {
            response.sendRedirect("/");
        }
    }
}
