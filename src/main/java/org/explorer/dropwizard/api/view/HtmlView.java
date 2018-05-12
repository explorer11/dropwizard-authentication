package org.explorer.dropwizard.api.view;

import io.dropwizard.views.View;
import org.explorer.dropwizard.api.User;

public class HtmlView extends View {
    private final User user;

    private HtmlView(User user) {
        super("html.mustache");

        this.user = user;
    }

    public static HtmlView of(final User user) {
        return new HtmlView(user);
    }

    public String getName() {
        return user.getName();
    }
}

