package com.undertow;

import business.User;
import jpa.EntityManagerHelper;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by opheusp on 11/10/16.
 */
@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName, password;
        User user;


        userName = request.getParameter("username");
        password = request.getParameter("password");
        System.out.println(userName +" "+ password);

        try{
            user = EntityManagerHelper.findUserWithUserName(userName);
            if (user != null){
                Cookie userCookie = new Cookie("username",userName);
                Cookie firstNameCookie = new Cookie("firstname",user.getFirstName());
                response.addCookie(userCookie);
                response.addCookie(firstNameCookie);
                //request.getRequestDispatcher("RprogramForm.html").forward(request,response);
            }
        }catch (NoResultException e){
            System.out.println("Error: No Entry Found");
            request.getRequestDispatcher("index.html").forward(request,response);

        }




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
