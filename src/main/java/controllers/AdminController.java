package controllers;

import Beans.BookBean;
import Beans.UserBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if(!isAdmin(request)) {
            request.getRequestDispatcher("/").forward(request, response);
        }

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

    public void showUserWishHistory(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();

        if(!isAdmin(request)) {
            request.getRequestDispatcher("/").forward(request, response);
        }
        int userId = Integer.parseInt(request.getParameter("userId"));
        SqlHandler handler = new SqlHandler();
        handler.connect();
        List<BookBean> userWishes = handler.getUserWishes(userId, true);
        handler.connect();

        request.setAttribute("user", handler.findUserById(Integer.toString(userId)));
        request.setAttribute("wishes", userWishes);

        request.getRequestDispatcher("userwishes.jsp").forward(request, response);
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session.getAttribute("isAdmin") != null) {
            return (Boolean) session.getAttribute("isAdmin");
        }
        return false;
    }
}
