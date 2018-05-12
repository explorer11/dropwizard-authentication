package org.explorer.dropwizard.api.view;

import io.dropwizard.views.View;
import org.explorer.dropwizard.api.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeView extends View {
    private final User user;

    private TimeView(User user) {
        super("time.mustache");

        this.user = user;
    }

    public static TimeView of(final User user) {
        return new TimeView(user);
    }

    public String getName() {
        return user.getName();
    }

    public String getTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME);
    }
}

