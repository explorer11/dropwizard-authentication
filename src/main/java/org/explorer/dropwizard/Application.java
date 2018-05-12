package org.explorer.dropwizard;

import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import org.explorer.dropwizard.api.User;
import org.explorer.dropwizard.core.BasicWithLogoutAuthFilter;
import org.explorer.dropwizard.resources.HtmlResource;
import org.explorer.dropwizard.resources.LogoutResource;
import org.explorer.dropwizard.resources.MainResource;
import org.explorer.dropwizard.resources.TimeResource;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

/**
 * java -jar target/dropwizard-authentication-1.0-SNAPSHOT.jar server config.yml
 */
public class Application extends io.dropwizard.Application<AuthenticationConfiguration> {

    public static void main(final String[] args) throws Exception {
        new Application().run(args);
    }

    @Override
    public String getName() {
        return "explorer-dropwizard";
    }

    @Override
    public void initialize(final Bootstrap<AuthenticationConfiguration> bootstrap) {
        bootstrap.addBundle(new ViewBundle<>());
        bootstrap.addBundle(new AssetsBundle("/assets/css", "/static/css", null, "css"));
        bootstrap.addBundle(new AssetsBundle("/assets/fonts", "/static/fonts", null, "fonts"));
        bootstrap.addBundle(new AssetsBundle("/assets/images", "/static/images", null, "images"));
        bootstrap.addBundle(new AssetsBundle("/assets/js", "/static/js", null, "js"));
    }

    @Override
    public void run(final AuthenticationConfiguration configuration,
                    final Environment environment) {
        final MainResource resource = new MainResource(
                configuration.getTemplate()
        );
        final HtmlResource htmlResource = new HtmlResource();
        final TimeResource timeResource = new TimeResource();
        final LogoutResource logoutResource = new LogoutResource();

        environment.jersey().register(resource);
        environment.jersey().register(htmlResource);
        environment.jersey().register(timeResource);
        environment.jersey().register(logoutResource);

        environment.jersey().register(new AuthDynamicFeature(
                new BasicWithLogoutAuthFilter<>()));
        environment.jersey().register(RolesAllowedDynamicFeature.class);
        //If you want to use @Auth to inject a custom Principal type into your resource
        environment.jersey().register(new AuthValueFactoryProvider.Binder<>(User.class));

        environment.jersey().register(new LogoutFilter());
    }

}
