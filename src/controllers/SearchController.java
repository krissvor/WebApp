package controllers;

import Beans.BookBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


public class SearchController {

    public enum SEARCHATTRIBUTE {
        KEY, PUB_TYPE, TITLE, AUTHOR, YEAR, VENUE
    }

    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("term");
        String attribute = request.getParameter("attr");

        SqlHandler handler = new SqlHandler();
        List<BookBean> books = handler.findBooksByTitle(searchTerm);
        for (BookBean book: books) {
            System.out.println(book);
        }
        request.setAttribute("BookBeans", books);
        request.getRequestDispatcher("BookList.jsp").forward(request, response);
    }
}
