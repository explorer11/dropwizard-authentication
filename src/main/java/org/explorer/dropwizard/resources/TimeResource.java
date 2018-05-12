package org.explorer.dropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import org.explorer.dropwizard.api.User;
import org.explorer.dropwizard.api.view.TimeView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/time")
@Produces(MediaType.TEXT_HTML)
public class TimeResource {

    @GET
    @Timed
    public Response html(@Auth User user) {
        return Response.ok()
                .entity(TimeView.of(user))
                .build();
    }
}
