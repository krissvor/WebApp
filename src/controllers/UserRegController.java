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


        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        sqlHandler.addUser(userBean);
        System.out.println("prøver å få alle brukere");
        sqlHandler.getAllUsers();
        System.out.println("har prøvd");

        sqlHandler.closeConnection();


        System.out.println(userBean.toString());

    }
}
