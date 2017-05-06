package controllers;

import Beans.BookBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by eirikvikanes on 06/05/2017.
 */
public class WishListController {


    public void showWishList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("userId") == null) {
            request.getRequestDispatcher("/").forward(request, response);
        }
        int userId = (Integer) session.getAttribute("userId");

        //If there are publications to attach to the response, set an attribute with the results
        SqlHandler handler = new SqlHandler();
        List<BookBean> wishedBooks = handler.getActiveUserWishes(userId);

        request.setAttribute("wishedBooks", wishedBooks);
        request.getRequestDispatcher("wishlist.jsp").forward(request, response);
    }
}
