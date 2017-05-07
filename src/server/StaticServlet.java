package server;


import controllers.BookController;
import controllers.LoginController;
import controllers.SearchController;
import controllers.UserRegController;
import controllers.*;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by kriss on 01-May-17.
 */
@WebServlet(name="StaticServlet")
public class StaticServlet extends HttpServlet {

    public StaticServlet() throws ParserConfigurationException, SAXException {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println(request.getRequestURI());
        if(request.getRequestURI().equals("/")) {
            BookController controller = new BookController();
            controller.viewRandomBooks(request, response);
        } else if (request.getRequestURI().startsWith("/search")) {
            SearchController controller = new SearchController();
            controller.search(request, response);
        } else if(request.getRequestURI().startsWith("/bookseller")) {
            request.getRequestDispatcher("Bookseller.jsp").forward(request, response);
        } else if (request.getRequestURI().startsWith("/book")) {
            BookController controller = new BookController();
            controller.viewSingleBook(request, response);
        } else if (request.getRequestURI().startsWith("/cart")) {
            CartController controller = new CartController();
            controller.showCart(request, response);
        } else if(request.getRequestURI().startsWith("/wishlist")) {
            WishListController controller = new WishListController();
            controller.showWishList(request, response);
        } else if(request.getRequestURI().startsWith("/register")) {
            request.getRequestDispatcher("UserRegistration.jsp").forward(request, response);
        } else {
            System.out.println("Tok i mot et get kall");
            String requestDispatcher = null;

            request.getRequestURL();
            SqlHandler sqlHandler = new SqlHandler();
            sqlHandler.connect();
            ArrayList users = sqlHandler.getAllUsers();
            sqlHandler.closeConnection();
            System.out.println(users.toString());


            request.setAttribute("users", users);

            requestDispatcher = "/UserList.jsp";

            request.getRequestDispatcher(requestDispatcher).forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Tok i mot et kall");
        System.out.println(request.toString());
        String action = request.getParameter("action");

        if(request.getRequestURI().startsWith("/cart")) {
            CartController controller = new CartController();
            controller.handleCartChange(request, response);
        } else if(request.getRequestURI().startsWith("/wishlist")) {
            WishListController controller = new WishListController();
            controller.handleWishListChange(request, response);
        } else if(request.getRequestURI().startsWith("/checkLogin")) {
            LoginController controller = new LoginController();
            controller.checkLogin(request, response);
        } else if(request.getRequestURI().startsWith("/logout")) {
            LoginController controller = new LoginController();
            controller.logout(request, response);
        }

        switch (action) {

            case ("UserRegistration"):
                System.out.println("Recieved User registration request");
                UserRegController userReg = new UserRegController();
                userReg.registerNewUser(request);
                break;

            case ("addBook"):
                System.out.println("trying to add book");
                BookController bookController = new BookController();
                bookController.addBook(request);
                break;
            case ("login"):
                System.out.println("trying to sign in");
                LoginController loginController = new LoginController();
                loginController.login(request, response);
                break;
            case ("deleteUser"):
                SqlHandler sqlHandler = new SqlHandler();
                sqlHandler.connect();
                int id = Integer.parseInt(request.getParameter("id"));
                sqlHandler.deleteUser(id);
                sqlHandler.closeConnection();
        }
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println("Checking for cart...");

        // Make sure a shopping cart session is created for the request
        HttpSession session = request.getSession();
        if(session.getAttribute("bookIds") == null) {
            session.setAttribute("bookIds", new HashSet<Integer>());
        }

        // Route the request to its intended location
        super.service(request, response);
    }
}

