package controllers;

import server.SqlHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by kriss on 03-May-17.
 */
public class LoginController {

    public LoginController(){

    }

    public boolean login(HttpServletRequest request){
        Boolean result;
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        SqlHandler sqlHandler = new SqlHandler();
        sqlHandler.connect();
        if(sqlHandler.verifyPassword(username, password)){
            result = true;
        }else
            result = false;

        sqlHandler.closeConnection();
        return result;

    }
}
