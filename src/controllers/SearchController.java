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
        String pageString = request.getParameter("page");
        int page = (pageString == null) ? 0 : Integer.parseInt(pageString);

        SqlHandler handler = new SqlHandler();
        List<BookBean> results = handler.findBooks(searchTerm, attributeForString(attribute), page);

        // Set the list that will be shown in the file
        request.setAttribute("BookBeans", results);

        // Set attributes for generation of pagination links
        request.setAttribute("page", page);
        request.setAttribute("attribute", attribute);
        request.setAttribute("term", searchTerm);

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
