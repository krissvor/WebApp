package controllers;

import Beans.UserBean;
import server.SqlHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

/**
 * Created by kriss on 03-May-17.
 */
public class UserRegController {

    public boolean registerNewUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean success;
        UserBean userBean = new UserBean();
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

        userBean.setCreditCard(request.getParameter("creditcard").trim());
        userBean.setEmail(request.getParameter("email").trim());
        userBean.setNickname(request.getParameter("nickname").trim());
        userBean.setPassword(request.getParameter("password").trim());
        userBean.setUsername(request.getParameter("username").trim());
        userBean.setAddress(request.getParameter("address").trim());
        userBean.setIs_active(false);

        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        if(sqlHandler.usernameExists(request.getParameter("username").trim())){
            System.out.println("Brukeren finnes allerede");
            success = false;
        }
        else{
            int userId = sqlHandler.addUser(userBean);
            System.out.println("ADDING USER");
            success = true;

            String link = "Click <a href=\"http://localhost:8081/confirmation?userId=" + userId + "\">here</a> to activate your user";
            Email.sendEmail(userBean.getEmail(),"DigitalLibrary","Activate your user",link,"localhost");
            response.sendRedirect("/");
        }


        sqlHandler.closeConnection();
        return success;
    }


    public void deleteUser(int id){
        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        sqlHandler.deleteUser(id);
        sqlHandler.closeConnection();
    }
}
