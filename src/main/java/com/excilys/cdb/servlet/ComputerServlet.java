package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ComputerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	final String pathInfo = request.getPathInfo();
	if (Objects.nonNull(pathInfo)) {
	    switch (pathInfo) {
	    case "/add":
		getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
		break;

	    default:
		break;
	    }
	}
    }

}
