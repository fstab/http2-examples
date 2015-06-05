package de.consol.research.h2c;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.util.concurrent.atomic.AtomicInteger;

@Path("/hello-world")
public class HelloWorldController {

    @GET
    @Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
    public String sayHello(@Context HttpServletRequest request) {
        AtomicInteger n = (AtomicInteger) request.getSession().getAttribute("n");
        if (n == null) {
            n = new AtomicInteger(0);
            request.getSession().setAttribute("n", n);
        }
        return "Hello, this is request number " + n.incrementAndGet();
    }
}
