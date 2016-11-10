package com.undertow;

import business.RProgram;
import jpa.EntityManagerHelper;
import org.json.JSONException;
import org.json.JSONObject;

import javax.persistence.NoResultException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by opheusp on 02/11/16.
 */
@WebServlet(name = "LoadToEditServlet")
public class LoadToEditServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        int id = Integer.parseInt(request.getPathInfo().substring(request.getPathInfo().lastIndexOf("/") + 1));

        System.out.println(request.getPathInfo());

        System.out.println("doGet LoadToEdit Servlet " + id);
        response.sendRedirect(request.getContextPath() + "/appEditor.html?id="+id);
    }
}
