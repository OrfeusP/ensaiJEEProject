package com.undertow;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.servlet;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.jsp.JspServletBuilder;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import java.io.File;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;

public class ServletServer {

	public static final String MYAPP = "/myapp";

	public static void main(final String[] args) {
		try {

			DeploymentInfo servletBuilder = deployment().setClassLoader(ServletServer.class.getClassLoader())
					.setContextPath(MYAPP).setDeploymentName("test.war")
					.setResourceManager(new FileResourceManager(new File("src/main/webapp"), 1024))
					.addServlets(
							servlet("subscribeServlet",SubscribeServlet.class).addMapping("/SubscribeServlet"),
							servlet("rinterpreterServlet", RinterpretationServlet.class).addMapping("/RinterpretationServlet"),
							servlet("loginServlet",LoginServlet.class).addMapping("/LoginServlet"),
							servlet("loadRprogamServlet",LoadRprogamServlet.class).addMapping("/LoadRprogamServlet"),
							servlet("loadToEditServlet",LoadToEditServlet.class).addMapping("/LoadToEditServlet/*"),
					 JspServletBuilder.createServlet("Default Jsp Servlet", "*.jsp")

			);

			DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
			manager.deploy();

			HttpHandler servletHandler = manager.start();
			PathHandler path = Handlers.path(Handlers.redirect(MYAPP)).addPrefixPath(MYAPP, servletHandler);


			Undertow server = Undertow.builder().addHttpListener(8080, "localhost").setHandler(path).build();
			server.start();
		} catch (ServletException e) {
			throw new RuntimeException(e);
		}
	}

}
