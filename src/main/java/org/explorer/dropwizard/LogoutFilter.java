package org.explorer.dropwizard;

import org.explorer.dropwizard.util.AuthenticationUtil;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.HttpHeaders;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@PreMatching
public class LogoutFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext) {
        boolean isLogout = requestContext.getUriInfo().getPath().contains(AuthenticationUtil.LOGOUT_PATH);
        if(isLogout) {
            Cookie cookie = requestContext.getCookies().get(AuthenticationUtil.COOKIE_NAME);
            if(cookie == null || AuthenticationUtil.COOKIE_DROP_VALUE.equals(cookie.getValue()))
                requestContext.getHeaders().replace(HttpHeaders.AUTHORIZATION, Stream.of("invalid").collect(Collectors.toList()));
        }
    }
}
