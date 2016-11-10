package com.undertow;

import business.RProgram;
import business.User;
import jpa.EntityManagerHelper;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.json.CookieList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.Column;
import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;


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


        try {
            user = EntityManagerHelper.findUserWithUserName(userName);
            if (user != null) {
                Cookie userCookie = new Cookie("username", userName);
                Cookie firstNameCookie = new Cookie("firstname", user.getFirstName());
                Cookie lastNameCookie = new Cookie("lastname", user.getLastName());
                Cookie emailCookie = new Cookie("email", user.getEmail());
                response.addCookie(userCookie);
                response.addCookie(firstNameCookie);
                response.addCookie(lastNameCookie);
                response.addCookie(emailCookie);

                System.out.println(userName + " " + password);
            }

        } catch (NoResultException e) {
            System.out.println("Error: No Entry Found");
            request.getRequestDispatcher("profile.html").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
