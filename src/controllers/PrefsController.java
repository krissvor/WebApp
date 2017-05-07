package controllers;

import Beans.BookBean;
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
}
