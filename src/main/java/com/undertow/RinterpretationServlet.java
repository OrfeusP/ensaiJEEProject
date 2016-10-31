package com.undertow;

import business.RProgram;
import jpa.EntityManagerHelper;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
import java.io.PrintWriter;

/**
 * Created by opheusp on 11/10/16.
 */
@WebServlet(name = "RinterpretationServlet")
public class RinterpretationServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = "";

        response.setContentType("text/html");

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
            RProgram p = new RProgram();
            System.out.println(userName);
            p.setAuthor(userName);
            p.setName(request.getParameter("filename"));
            p.setProgram(request.getParameter("Rprogram"));
            p.setResult(buffer.toString());
            p.updateTimeStamps();
            System.out.println(p.getResult());
            EntityManagerHelper.addRprogram(p);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        PrintWriter out = response.getWriter();

        out.println("<HTML>\n<BODY>\n" + buffer.toString().replaceAll("\n", "<BR>\n") + "</BODY></HTML>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
