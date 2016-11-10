package com.undertow;

import business.RProgram;
import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import jpa.EntityManagerHelper;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by opheusp on 10/11/16.
 */
public class DisplayResultServlet extends HttpServlet{

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getParameter("id"));

        System.out.println("Id Test: "+id);

        System.out.println("doGet");
        try{

            response.setContentType("application/json");
            RProgram  program;

            JSONObject obj = new JSONObject();

            program = EntityManagerHelper.findProgramOfUser(id);

            obj.put("filename",program.getName());
            obj.put("result",program.getResult().replaceAll("\n", "<BR>\n"));
            PrintWriter out = response.getWriter();
            out.println(obj);
        }catch(JSONException e){

        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("doPost");
    }
    }
