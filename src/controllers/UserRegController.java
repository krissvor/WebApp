package controllers;

import Beans.UserBean;

import javax.servlet.http.HttpServletRequest;

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
            userBean.setBirthYear(year);
        }catch(Exception e){
            System.out.println("noe skjedde med året");
            System.out.println(e.getMessage());
        }

        userBean.setCreditCard(request.getParameter("creditcard"));
        userBean.setEmail(request.getParameter("email"));
        userBean.setNickname(request.getParameter("nickname"));
        userBean.setPassword(request.getParameter("password"));
        userBean.setUsername(request.getParameter("username"));

        System.out.println(userBean.toString());

    }
}
