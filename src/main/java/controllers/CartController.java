package controllers;

import Beans.BookBean;
import server.SqlHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CartController {

    public void showCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        HttpSession session = request.getSession();
        Set<Integer> bookIds = (Set<Integer>) session.getAttribute("bookIds");

        //If there are publications to attach to the response, set an attribute with the results
        List<BookBean> booksInCart = new ArrayList<BookBean>();
        SqlHandler handler = new SqlHandler();
        if(bookIds.size() > 0) {
            for(Integer id : bookIds) {
                booksInCart.add(handler.getSingleBook(id, true));
            }
        }
        request.setAttribute("cartBooks", booksInCart);
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    public void handleCartChange(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if(action == null) {
            response.sendRedirect("/");
        } else if(action.equals("add")) {
            addToCart(request, response);
        } else if(action.equals("remove")) {
            removeFromCart(request, response);
        } else {
            response.sendRedirect("/");
        }
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        if(idString == null) {
            // Throw error
        }
        int id = Integer.parseInt(idString);
        ((Set<Integer>) request.getSession().getAttribute("bookIds")).add(id);
        this.showCart(request, response);
    }

    private void removeFromCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");
        if(idString == null) {
            // Throw error
        }
        int id = Integer.parseInt(idString);
        ((Set<Integer>) request.getSession().getAttribute("bookIds")).remove(id);
        this.showCart(request, response);
    }
}
