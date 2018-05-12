package org.explorer.dropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import io.dropwizard.auth.Auth;
import org.explorer.dropwizard.api.User;
import org.explorer.dropwizard.util.AuthenticationUtil;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("/logout")
@Produces(MediaType.TEXT_HTML)
public class LogoutResource {

    @POST
    @Timed
    public Response logout(@Auth final User user, @Context final UriInfo uriInfo) {
        return Response.ok()
                .header(HttpHeaders.SET_COOKIE, AuthenticationUtil.COOKIE_NAME + "=" + AuthenticationUtil.COOKIE_DROP_VALUE)
                .build();
    }
}
