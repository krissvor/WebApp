package server;

import Beans.UserBean;
import controllers.BookController;
import controllers.LoginController;
import controllers.UserRegController;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by kriss on 01-May-17.
 */
@WebServlet(name="StaticServlet")
@MultipartConfig
public class StaticServlet extends HttpServlet {

    public StaticServlet() throws ParserConfigurationException, SAXException {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(request.getRequestURI().startsWith("/checkUsername")){
            String username = request.getParameter("username");
            SqlHandler sqlHandler = new SqlHandler();
            sqlHandler.connect();
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
            if(sqlHandler.usernameExists(username)){
                out.write("true");

            }else
                out.write("false");
            sqlHandler.closeConnection();
        }
        if(request.getRequestURI().startsWith("/UserRegistration")) {
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
        String requestDispatcher = null;
        System.out.println("Tok i mot et kall");
        System.out.println(request.toString());
        String action = request.getParameter("action");

        switch (action){

            case("UserRegistration"):
                System.out.println("Recieved User registration request");
                UserRegController userReg = new UserRegController();
                if(userReg.registerNewUser(request)){

                }
                else{
                    requestDispatcher = "/UserRegistration.jsp";

                    request.getRequestDispatcher(requestDispatcher).forward(request, response);
                }
                break;

            case("addBook"):
                System.out.println("trying to add book");
                BookController bookController = new BookController();
                bookController.addBook(request);
                break;
            case("login"):
                System.out.println("trying to sign in");
                LoginController loginController = new LoginController();
                loginController.login(request);
                break;
            case("deleteUser"):
                SqlHandler sqlHandler = new SqlHandler();
                sqlHandler.connect();
                int id = Integer.parseInt(request.getParameter("id"));
                sqlHandler.deleteUser(id);
                sqlHandler.closeConnection();

                break;
        }

    }

}


