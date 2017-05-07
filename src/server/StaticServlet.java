package server;


import Beans.UserBean;
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
import java.io.PrintWriter;
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
        if (request.getRequestURI().equals("/")) {
            BookController controller = new BookController();
            controller.viewRandomBooks(request, response);
        } else if (request.getRequestURI().startsWith("/search")) {
            SearchController controller = new SearchController();
            controller.search(request, response);
        } else if (request.getRequestURI().startsWith("/bookseller")) {
            request.getRequestDispatcher("Bookseller.jsp").forward(request, response);
        } else if (request.getRequestURI().startsWith("/book")) {
            BookController controller = new BookController();
            controller.viewSingleBook(request, response);
        } else if (request.getRequestURI().startsWith("/cart")) {
            CartController controller = new CartController();
            controller.showCart(request, response);
        } else if (request.getRequestURI().startsWith("/wishlist")) {
            WishListController controller = new WishListController();
            controller.showWishList(request, response);
        } else if (request.getRequestURI().startsWith("/register")) {
            request.getRequestDispatcher("UserRegistration.jsp").forward(request, response);
        } else if (request.getRequestURI().startsWith("/prefs")) {
            PrefsController controller = new PrefsController();
            controller.viewPrefs(request, response);
        } else if (request.getRequestURI().startsWith("/confirmation")) {
            ValidationController controller = new ValidationController();
            controller.confirmUser(request, response);
        } else {

            SqlHandler sqlHandler = new SqlHandler();

            if (request.getRequestURI().startsWith("/search")) {
                SearchController controller = new SearchController();
                controller.search(request, response);
            } else if (request.getRequestURI().startsWith("/checkUsername")) {
                String username = request.getParameter("username");
                sqlHandler.connect();
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                if (sqlHandler.usernameExists(username)) {
                    out.write("true");

                } else
                    out.write("false");
                sqlHandler.closeConnection();
            } else if (request.getRequestURI().startsWith("/UserRegistration")) {
                System.out.println("Tok i mot et get kall");
                String requestDispatcher = null;

                request.getRequestURL();
                sqlHandler.connect();
                ArrayList users = sqlHandler.getAllUsers();
                sqlHandler.closeConnection();
                System.out.println(users.toString());


                request.setAttribute("users", users);

                requestDispatcher = "/UserList.jsp";


                request.getRequestDispatcher(requestDispatcher).forward(request, response);
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestDispatcher = null;
        System.out.println("Tok i mot et kall");
        System.out.println(request.toString());
        String action = request.getParameter("action");
        System.out.println("action is: " + action);
        SqlHandler sqlHandler;

        if (request.getRequestURI().startsWith("/cart")) {
            CartController controller = new CartController();
            controller.handleCartChange(request, response);
        } else if (request.getRequestURI().startsWith("/wishlist")) {
            WishListController controller = new WishListController();
            controller.handleWishListChange(request, response);
        } else if (request.getRequestURI().startsWith("/logout")) {
            LoginController controller = new LoginController();
            controller.logout(request, response);
        } else if (request.getRequestURI().startsWith("/prefs")) {
            PrefsController controller = new PrefsController();
            controller.handlePost(request, response);
        } else if (request.getRequestURI().startsWith("/addBook")) {
            System.out.println("trying to add book");
            BookController bookController = new BookController();
            bookController.addBook(request);
        } else if (request.getRequestURI().startsWith("/register")) {
            UserRegController userReg = new UserRegController();
            userReg.registerNewUser(request);
        }else if(request.getRequestURI().startsWith(("/toggleActive"))){
                sqlHandler = new SqlHandler();
                sqlHandler.connect();
                sqlHandler.toggleActive(Integer.parseInt(request.getParameter("id")), Integer.parseInt(request.getParameter("isActive")));
                sqlHandler.closeConnection();
        }
        if (action != null) {
            switch (action) {

                case ("checkLogin"):
                    HttpSession session = request.getSession();

                    sqlHandler = new SqlHandler();

                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    sqlHandler.connect();
                    response.setContentType("text/html");

                    PrintWriter out = response.getWriter();
                    System.out.println("trying to sign in");
                    LoginController loginController = new LoginController();
                    UserBean user = loginController.login(request);
                    if (user != null) {
                        System.out.println(action);
                        session.setAttribute("userId", user.getId());
                        session.setAttribute("name", user.getFirstName() + " " + user.getLastName());
                        session.setAttribute("username", user.getUsername());
                        response.sendRedirect("/");
                        out.write("true");

                    } else {
                        out.write("false");
                    }
                    sqlHandler.closeConnection();

                    break;
                case ("deleteUser"):
                    sqlHandler = new SqlHandler();
                    sqlHandler.connect();
                    int id = Integer.parseInt(request.getParameter("id"));
                    sqlHandler.deleteUser(id);
                    sqlHandler.closeConnection();
                    break;

                case("purchase"):
                    HttpSession s = request.getSession();
                    HashSet<Integer> books = (HashSet<Integer>) s.getAttribute("bookIds");
                    s.removeAttribute("bookIds");

                    PurchaseController purchase = new PurchaseController(books);

                    response.sendRedirect( "/cart.jsp");



                    break;
            }
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

