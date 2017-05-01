package server;

import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

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
        System.out.println("Tok i mot et kall");

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Tok i mot et kall");
        System.out.println(request.toString());
        String action = request.getParameter("action");

            switch (action){

                case("UserRegistration"):
                    System.out.println("Recieved User registration request");
                    System.out.println("firstname = " +request.getParameter("firstname"));

                    break;

                case("addBook"):
                    System.out.println("trying to add book");
            }

        }

    }


