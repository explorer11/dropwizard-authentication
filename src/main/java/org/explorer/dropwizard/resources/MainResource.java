package org.explorer.dropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import org.explorer.dropwizard.api.Pojo;
import org.explorer.dropwizard.api.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
public class MainResource {

    private final String template;
    private final AtomicLong counter;

    public MainResource(String template) {
        this.template = template;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public Pojo sayHello(@Auth User user) {
        final String value = String.format(template, user.getName());
        return new Pojo(counter.incrementAndGet(), value);
    }
}
