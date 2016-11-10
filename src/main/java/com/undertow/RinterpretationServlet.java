package com.undertow;

import business.RProgram;
import jpa.EntityManagerHelper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by opheusp on 11/10/16.
 */
@WebServlet(name = "RinterpretationServlet")
public class RinterpretationServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = "";

        JSONObject program = new JSONObject();




        response.setContentType("application/json");

        System.out.println(request.getRequestURI());
        // Redirect standard output
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        ScriptEngineManager manager = new ScriptEngineManager();
        // create a Renjin engine:
        ScriptEngine engine = manager.getEngineByName("Renjin");
        // check if the engine has loaded correctly:
        if (engine == null) {
            throw new RuntimeException("Renjin Script Engine not found on the classpath.");
        }
        engine.getContext().setWriter(new PrintWriter(buffer));

        try {
            engine.eval(request.getParameter("Rprogram"));

            Cookie[] cookies = request.getCookies();


            for (int i = 0; i < cookies.length ; i++){
                Cookie cookie = cookies[i];
                if (cookie.getName().equals("username")){
                    userName = cookie.getValue();
                    break;
                }
            }

            RProgram p;
            String id_param = request.getParameter("id");


            if(id_param != null){

                int id = Integer.parseInt(id_param);
                p = EntityManagerHelper.findProgramOfUser(id);
                EntityManagerHelper.updateFilename(id,request.getParameter("filename"));
                EntityManagerHelper.updateProgramcode(id,request.getParameter("Rprogram"));
                EntityManagerHelper.updateProgramresult(id,buffer.toString());
                EntityManagerHelper.updateTimeStamps(id);

            }
            else{

                p = new RProgram();
                System.out.println(userName);
                p.setAuthor(userName);
                p.setName(request.getParameter("filename"));
                p.setProgram(request.getParameter("Rprogram"));
                p.setResult(buffer.toString());
                p.updateTimeStamps();
                EntityManagerHelper.addRprogram(p);
            }

            program.put("id",p.getId());
            PrintWriter out = response.getWriter();
            out.println(program);

        }catch (NoResultException e) {
            System.out.println("No program in the DB with this name");
        }catch (Exception e){

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        JSONObject programObj;

        response.setContentType("application/json");

        try {


            int id = Integer.parseInt(request.getParameter("id"));

            RProgram program = EntityManagerHelper.findProgramOfUser(id);


            programObj = new JSONObject();
            programObj.put("id",program.getId());
            programObj.put("filename",program.getName());
            programObj.put("code",program.getProgramCode());


            System.out.println(programObj);
            PrintWriter out = response.getWriter();
            out.println(programObj);

        }catch (NoResultException e){
            EntityManagerHelper.rollback();
        }catch(JSONException e){

        }
    }
}
