package controllers;

import Beans.BookBean;
import com.sun.deploy.net.HttpRequest;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by eirikvikanes on 07/05/2017.
 */
public class PrefsController {

    public void viewPrefs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");

        SqlHandler handler = new SqlHandler();
        List<BookBean> books = handler.getBooksForUser(userId);

        request.setAttribute("user", null);
        request.setAttribute("books", books);
        request.getRequestDispatcher("prefs.jsp").forward(request, response);
    }

    public void removeBookFromSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");
        System.out.println(userId);
        int bookId = Integer.parseInt(request.getParameter("id"));
        System.out.println(bookId);
        SqlHandler handler = new SqlHandler();
        handler.removeBookFromSale(bookId, userId);
        response.sendRedirect("/prefs");
    }
}
