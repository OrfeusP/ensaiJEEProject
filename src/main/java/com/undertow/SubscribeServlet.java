package com.undertow;

import jpa.EntityManagerHelper;
import business.User;

import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by opheusp on 11/10/16.
 */
public class SubscribeServlet extends HttpServlet {

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Enumeration enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String parameterName = (String) enumeration.nextElement();
            System.out.println(parameterName);
        }

        try {
            User newUser = new User();


            newUser.setEmail(request.getParameter("email"));
            newUser.setFirstName(request.getParameter("fname"));
            newUser.setLastName(request.getParameter("lname"));
            newUser.setUserName(request.getParameter("username"));
            newUser.setPassword(request.getParameter("password"));

            System.out.println(request.getParameter("email"));
            System.out.println(request.getParameter("fname"));
            System.out.println(request.getParameter("lname"));
            System.out.println(request.getParameter("username"));
            System.out.println(request.getParameter("password"));

            Cookie userCookie = new Cookie("username",newUser.getUserName());
            Cookie firstNameCookie = new Cookie("firstname",newUser.getFirstName());
            Cookie lastNameCookie = new Cookie("lastname",newUser.getLastName());
            Cookie emailCookie = new Cookie("email",newUser.getEmail());
            response.addCookie(userCookie);
            response.addCookie(firstNameCookie);
            response.addCookie(lastNameCookie);
            response.addCookie(emailCookie);

            EntityManagerHelper.addUser(newUser);
            request.getRequestDispatcher("index.html").forward(request,response);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
