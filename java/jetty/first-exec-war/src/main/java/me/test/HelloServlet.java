package me.test;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public HelloServlet() {
        super();
        URL url = HelloServlet.class.getResource("HelloServlet.class");
        if (url != null) {
            System.out.println("]]]]]]]]]]]]]]]]]] HelloServlet ：" + url);
            System.out.println("]]]]]]]]]]]]]]]]]] HelloServlet ：" + url.getFile());
            System.out.println("]]]]]]]]]]]]]]]]]] HelloServlet ：" + new File(url.getFile()).exists());
            System.out.println("]]]]]]]]]]]]]]]]]] HelloServlet ：" + url.toExternalForm());
        } else {

        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        String now = DateTimeFormat.forPattern("yyyy/MM/dd HH:mm:ss.SSS Z").print(DateTime.now());

        String user = request.getParameter("user");
        if (StringUtils.isBlank(user)) {
            user = "Guset";
        }

        PrintWriter out = response.getWriter();
        out.write(String.format("%s : Hello %s", now, user));
        out.flush();
    }

}
