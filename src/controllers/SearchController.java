package controllers;

import Beans.BookBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static controllers.SearchController.SEARCHATTRIBUTE.*;

public class SearchController {

    public enum SEARCHATTRIBUTE {
        KEY, PUB_TYPE, TITLE, AUTHOR, YEAR, VENUE
    }

    public void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String searchTerm = request.getParameter("term");
        String attribute = request.getParameter("attribute");

        SqlHandler handler = new SqlHandler();
        List<BookBean> results = handler.findBooks(searchTerm, attributeForString(attribute));
        for (BookBean book: results) {
            System.out.println(book);
        }
        request.setAttribute("BookBeans", results);
        request.getRequestDispatcher("BookList.jsp").forward(request, response);
    }

    private SEARCHATTRIBUTE attributeForString(String s) {
        switch(s) {
            case "Year": return YEAR;
            case "Author": return AUTHOR;
            case "Venue": return VENUE;
            case "Title": return TITLE;
            default: return null;
        }
    }
}
