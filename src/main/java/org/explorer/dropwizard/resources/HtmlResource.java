package org.explorer.dropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import org.explorer.dropwizard.api.User;
import org.explorer.dropwizard.api.view.HtmlView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/page")
@Produces(MediaType.TEXT_HTML)
public class HtmlResource {

    @GET
    @Timed
    public Response html(@Auth User user) {
        return Response.ok()
                .entity(HtmlView.of(user))
                .build();
    }
}
