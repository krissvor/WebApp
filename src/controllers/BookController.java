package controllers;

import Beans.BookBean;
import Beans.UserBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by kriss on 03-May-17.
 */
public class BookController {

    public BookController(){

    }

    public void addBook(HttpServletRequest request){
        HttpSession session = request.getSession();
        int userId = -1;
        Object userObject = session.getAttribute("userId");
        if ((userObject != null) && (userObject instanceof Integer)) {
            userId = ((Integer) userObject).intValue();
        }
        BookBean book = new BookBean();
        book.setTitle(request.getParameter("title"));
        book.setPublicationType(request.getParameter("publicationType"));
        book.setPublicationDate(request.getParameter("publicationDate"));
        book.setPrice(request.getParameter("price"));
        book.setVenues(request.getParameter("venue"));
        book.setPages(request.getParameter("pages"));
        book.setUrl(request.getParameter("url"));
        book.setEe(request.getParameter("ee"));


        ArrayList<String> usrs = new ArrayList<String>();

        String[] authors = request.getParameter("author").split(",");
        for (int i = 0; i <authors.length ; i++) {
            usrs.add(authors[i].trim());
        }


        book.setAuthor(usrs);

        SqlHandler sq = new SqlHandler();
        sq.connect();
        sq.addBook(book, userId);
        sq.closeConnection();


        System.out.println(book.toString());
    }

    public void viewSingleBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getParameter("id") != null) {
            int bookId = Integer.parseInt(request.getParameter("id"));
            SqlHandler handler = new SqlHandler();
            BookBean book = handler.getSingleBook(bookId, true);

            request.setAttribute("bookBean", book);
            request.getRequestDispatcher("singleBook.jsp").forward(request, response);
        }
    }

    public void viewRandomBooks(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int randomCount = 10;
        int booksCount = 2871;
        ArrayList<BookBean> books = new ArrayList<>();
        SqlHandler handler = new SqlHandler();
        while(books.size() < randomCount) {
            int randId = new Double(Math.random() * booksCount).intValue();
            books.add(handler.getSingleBook(randId, true));
        }
        request.setAttribute("randomBooks", books);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
