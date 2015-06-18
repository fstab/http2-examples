package de.consol.research.h2c;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

public class HelloWorldServlet extends HttpServlet {


    // Mapped to https://localhost:8443/hello-world/api/hello-world
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("counter") == null) {
            session.setAttribute("counter", new AtomicInteger(0));
        }
        int reqNr = ((AtomicInteger) session.getAttribute("counter")).incrementAndGet();
        PrintWriter writer = resp.getWriter();
        writer.write("Hello, World!\n");
        writer.write("Btw, this is request number " + reqNr + ".");
        writer.close();
    }
}
