package com.undertow;

import business.RProgram;
import business.User;
import jpa.EntityManagerHelper;
import org.json.JSONArray;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by opheusp on 14/10/16.
 */
public class LoadRprogamServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName, password;
        LinkedList <RProgram> programList = new LinkedList<RProgram>();


        userName = request.getParameter("username");
        password = request.getParameter("password");
        System.out.println(userName +" "+ password);

        try{

            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length ; i++){
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("username")){
                    userName = cookie.getValue();
                    break;
                }
            }
            //programList = EntityManagerHelper.findProgramsOfUser(userName);
            //JSONArray JSONArray = new JSONArray(programList);

        }catch (NoResultException e){
            System.out.println("Error: No Entry Found");
            request.getRequestDispatcher("index.html").forward(request,response);

        }
    }
}
