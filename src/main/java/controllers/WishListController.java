package controllers;

import Beans.BookBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by eirikvikanes on 06/05/2017.
 */
public class WishListController {


    public void showWishList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("userId") == null) {
            request.getRequestDispatcher("/").forward(request, response);
        }
        int userId = (Integer) session.getAttribute("userId");

        //If there are publications to attach to the response, set an attribute with the results
        SqlHandler handler = new SqlHandler();
        List<BookBean> wishedBooks = handler.getUserWishes(userId, false);

        request.setAttribute("wishedBooks", wishedBooks);
        request.getRequestDispatcher("wishlist.jsp").forward(request, response);
    }

    public void handleWishListChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action == null) {
            response.sendRedirect("/");
        } else if (action.equals("add")) {
            addToWishList(request, response);
        } else if (action.equals("remove")) {
            removeFromWishList(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    private void addToWishList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        int userId = (Integer) request.getSession().getAttribute("userId");

        SqlHandler handler = new SqlHandler();
        handler.addWish(userId, bookId);

        this.showWishList(request, response);

    }

    private void removeFromWishList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int bookId = Integer.parseInt(request.getParameter("id"));
        int userId = (Integer) request.getSession().getAttribute("userId");

        SqlHandler handler = new SqlHandler();
        handler.removeWish(userId, bookId);

        this.showWishList(request, response);
    }
}
