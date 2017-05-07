package controllers;

import Beans.BookBean;
import Beans.UserBean;
import com.sun.deploy.net.HttpRequest;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * Created by eirikvikanes on 07/05/2017.
 */
public class PrefsController {

    public void viewPrefs(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");
        SqlHandler handler = new SqlHandler();
        handler.connect();
        UserBean user = handler.findUserById(Integer.toString(userId));
        List<BookBean> books = handler.getBooksForUser(userId);

        request.setAttribute("user", user);
        request.setAttribute("books", books);
        request.getRequestDispatcher("prefs.jsp").forward(request, response);
    }

    public void handlePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        System.out.println("PREFS ACTION: " + action);
        if(action.equals("remove")) {
            removeBookFromSale(request, response);
        } else if(action.equals("updateUser")) {
            updateUser(request, response);
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserBean userBean = new UserBean();
        userBean.setId((Integer) request.getSession().getAttribute("userId"));
        userBean.setFirstName(request.getParameter("firstname"));
        userBean.setLastName(request.getParameter("lastname"));
        try {
            int year = Integer.parseInt(request.getParameter("year"));
            int currentYear = Calendar.getInstance().get(Calendar.YEAR);
            if(year <= currentYear && year>(currentYear-120)) {
                userBean.setBirthYear(year);
            }
        }catch(Exception e){
            System.out.println("noe skjedde med året");
            System.out.println(e.getMessage());
        }

        userBean.setCreditCard(request.getParameter("creditcard"));
        userBean.setEmail(request.getParameter("email"));
        userBean.setNickname(request.getParameter("nickname"));
        userBean.setPassword(request.getParameter("password"));
        userBean.setUsername(request.getParameter("username"));
        userBean.setAddress(request.getParameter("address"));
        System.out.println("BEan: "+ userBean);

        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        sqlHandler.updateUser(userBean);

        response.sendRedirect("/prefs");
    }

    public void removeBookFromSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = (Integer) request.getSession().getAttribute("userId");
        System.out.println(userId);
        int bookId = Integer.parseInt(request.getParameter("id"));
        System.out.println(bookId);
        SqlHandler handler = new SqlHandler();
        handler.removeBookFromSale(bookId, userId);
        response.sendRedirect("/prefs");
    }
}
