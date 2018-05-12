package org.explorer.dropwizard.core;

import io.dropwizard.auth.Authorizer;
import org.explorer.dropwizard.api.User;

public final class ExampleAuthorizer implements Authorizer<User> {

    @Override
    public boolean authorize(User user, String role) {
        return user.getName().equals("user") && role.equals("ADMIN");
    }
}
