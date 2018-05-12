package org.explorer.dropwizard.core;

import io.dropwizard.auth.AuthFilter;
import io.dropwizard.auth.basic.BasicCredentialAuthFilter;
import io.dropwizard.auth.basic.BasicCredentials;
import org.explorer.dropwizard.api.User;
import org.explorer.dropwizard.util.AuthenticationUtil;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.security.Principal;

@Priority(Priorities.AUTHENTICATION)
public final class BasicWithLogoutAuthFilter<P extends Principal> extends AuthFilter<BasicCredentials, P> {

    private AuthFilter authFilter;

    public BasicWithLogoutAuthFilter() {
        this.authFilter = new BasicCredentialAuthFilter.Builder<User>()
                .setAuthenticator(new ExampleAuthenticator())
                .setAuthorizer(new ExampleAuthorizer())
                .setRealm(AuthenticationUtil.REALM)
                .buildAuthFilter();
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        boolean isLogout = requestContext.getUriInfo().getPath().contains(AuthenticationUtil.LOGOUT_PATH);
        if(isLogout) {
            try {
                this.authFilter.filter(requestContext);
            } catch (WebApplicationException e) {
                throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, String.format("%s realm=\"%s\"", "Basic", AuthenticationUtil.REALM))
                        .header(HttpHeaders.SET_COOKIE, AuthenticationUtil.COOKIE_NAME + "=" + AuthenticationUtil.COOKIE_SET_VALUE)
                        .type(MediaType.TEXT_PLAIN_TYPE)
                        .entity("Credentials are required to access this resource.")
                        .build());
            }
        } else
            this.authFilter.filter(requestContext);
    }
}
