package controllers;

import Beans.UserBean;
import server.SqlHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by kriss on 03-May-17.
 */
public class UserRegController {

    public void registerNewUser(HttpServletRequest request){
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

        userBean.setCreditCard(request.getParameter("creditcard"));
        userBean.setEmail(request.getParameter("email"));
        userBean.setNickname(request.getParameter("nickname"));
        userBean.setPassword(request.getParameter("password"));
        userBean.setUsername(request.getParameter("username"));
        userBean.setIs_active(false);

        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        sqlHandler.addUser(userBean);
        System.out.println("prøver å få alle brukere");
        sqlHandler.getAllUsers();
        System.out.println("har prøvd");


        int userId = userBean.getId();
        String link = "Click <a href=\"http://127.0.0.1:8080/confirmation.jsp?userId=" + userId + "\">here</a> to activate your user";



        Email.sendEmail(userBean.getEmail(),"DigitalLibrary","Activate your user",link,"localhost");

        sqlHandler.closeConnection();

        System.out.println(userBean.toString());

    }
}
