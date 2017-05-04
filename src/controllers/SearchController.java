package controllers;

import Beans.BookBean;
import server.SqlHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class SearchController {

    public enum SEARCHATTRIBUTE {
        KEY, PUB_TYPE, TITLE, AUTHOR, YEAR, VENUE
    }

    public void search(HttpServletRequest request, HttpServletResponse response) {
        String searchTerm = request.getParameter("term");
        String attribute = request.getParameter("attr");

        SqlHandler handler = new SqlHandler();
        List<BookBean> books = handler.findBooksByTitle(searchTerm);
        for(BookBean book : books) {
            System.out.println(book);
        }
    }
}
