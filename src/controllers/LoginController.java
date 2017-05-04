package controllers;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kriss on 03-May-17.
 */
public class LoginController {

    public LoginController(){

    }

    public boolean login(HttpServletRequest request){
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Username = " + username + ".... Password = " + password +
        "\n");

        return true;
    }
}
