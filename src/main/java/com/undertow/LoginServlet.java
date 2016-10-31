package com.undertow;

import business.RProgram;
import business.User;
import jpa.EntityManagerHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        ArrayList<RProgram> programList = null;
        JSONObject json = new JSONObject();

        JSONObject  program = new JSONObject();

        //response.setContentType("application/json");
        userName = request.getParameter("username");
        password = request.getParameter("password");


        try{
            user = EntityManagerHelper.findUserWithUserName(userName);
            if (user != null){
                Cookie userCookie = new Cookie("username",userName);
                Cookie firstNameCookie = new Cookie("firstname",user.getFirstName());
                response.addCookie(userCookie);
                response.addCookie(firstNameCookie);

                programList = EntityManagerHelper.findProgramsOfUser(userName);
                System.out.println(userName +" "+ password);
            }

            for (int i = 0; i < programList.size(); i++) {
                JSONArray programsAttributes = new JSONArray();
                programsAttributes.put(programList.get(i).getId());
                programsAttributes.put(programList.get(i).getName());
                programsAttributes.put(programList.get(i).getDateCreated());
                programsAttributes.put(programList.get(i).getLastModified());
                program.put("Rprogram"+i,programsAttributes);

            }


            request.getRequestDispatcher("profile.html").forward(request,response);
            System.out.println(program);

        }catch (NoResultException e){
            System.out.println("Error: No Entry Found");
            request.getRequestDispatcher("index.html").forward(request, response);
        }catch(JSONException e){

        }




    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
