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
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by opheusp on 14/10/16.
 */
@WebServlet(name = "LoadRprogamServlet")
public class LoadRprogamServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        JSONObject program;
        JSONArray arrayObjOuter = new JSONArray();


        String userName = "";
        ArrayList <RProgram> programList;


        response.setContentType("application/json");

        try{

            Cookie[] cookies = request.getCookies();
            for (int i = 0; i < cookies.length ; i++){
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("username")){
                    userName = cookie.getValue();
                    break;
                }
            }

            programList = EntityManagerHelper.findProgramsOfUser(userName);


            for (int i = 0; i < programList.size(); i++) {

                program = new JSONObject();

                program.put("id",programList.get(i).getId());
                program.put("filename",programList.get(i).getName());
                program.put("lastModified",programList.get(i).getLastModified());
                program.put("created",programList.get(i).getDateCreated());

                arrayObjOuter.put(program);
            }

            PrintWriter out = response.getWriter();
            out.println(arrayObjOuter);


            System.out.println(arrayObjOuter);


        }catch (NoResultException e){
            System.out.println("Error: No Entry Found");
        } catch(JSONException e){
            System.out.println("JSon Exception");
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try{

            int id = Integer.parseInt(request.getParameter("id"));


            System.out.println(id);

            EntityManagerHelper.deleteProgramFromUser(id);

        }catch (NoResultException e){
            //EntityManagerHelper.rollback();

        }
    }
}
