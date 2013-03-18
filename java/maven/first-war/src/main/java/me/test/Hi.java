package me.test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

public class Hi extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Hi() {
    }

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {

        // Goals :
        // check whether inspect var's value and acttach source code

        // Result :
        // > can inspect var "name"'s value
        // > m2e will auto download and attach source for libraries.
        // > can NOT inspect any var's value in StringUtils.isEmpty()

        // ERROR message:
        // Evaluations must contain either an expression or a block of
        // well-formed statements
        String name = request.getParameter("name");
        if (StringUtils.isEmpty(name)) {
            name = "Guest";
        }
        response.getWriter().write("Hi " + name + " !");
    }
}
