package controllers;

import Beans.BookBean;
import server.SqlHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by kriss on 03-May-17.
 */
public class BookController {

    public BookController(){

    }

    public void addBook(HttpServletRequest request){
        BookBean book = new BookBean();
        book.setTitle(request.getParameter("title"));
        book.setPublicationType(request.getParameter("publicationType"));
        book.setPublicationDate(request.getParameter("publicationDate"));
        book.setPrice(request.getParameter("price"));
        book.setVenues(request.getParameter("venue"));
        book.setPages(request.getParameter("pages"));
        book.setUrl(request.getParameter("url"));
        book.setEe(request.getParameter("ee"));


        String[] authors = request.getParameter("author").split(",");
        for (int i = 0; i <authors.length ; i++) {
            authors[i] = authors[i].trim();
        }
        book.setAuthor(authors);

        SqlHandler sq = new SqlHandler();
        sq.connect();
        int bookId = sq.addBook(book);
        System.out.println(bookId);

        System.out.println(book.toString());
    }
}
