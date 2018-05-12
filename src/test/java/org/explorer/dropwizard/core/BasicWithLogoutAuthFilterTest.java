package org.explorer.dropwizard.core;

import com.google.common.io.BaseEncoding;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.testing.junit.ResourceTestRule;
import org.explorer.dropwizard.api.User;
import org.explorer.dropwizard.resources.MainResource;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.glassfish.jersey.test.grizzly.GrizzlyWebTestContainerFactory;
import org.junit.Rule;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicWithLogoutAuthFilterTest {

    @Rule
    public ResourceTestRule rule = ResourceTestRule
            .builder()
            .setTestContainerFactory(new GrizzlyWebTestContainerFactory())
            .addProvider(new AuthDynamicFeature(new BasicWithLogoutAuthFilter<User>()))
            .addProvider(RolesAllowedDynamicFeature.class)
            .addProvider(new AuthValueFactoryProvider.Binder<>(User.class))
            .addResource(new MainResource("testMain"))
            .build();

    @Test
    public void shouldAuthenticate(){
        final Response response = rule.getJerseyTest().target("/hello-world")
                .request(MediaType.APPLICATION_JSON_TYPE)
                        .header("Authorization", "Basic " + BaseEncoding.base64().encode("user:secret".getBytes()))
                .get();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    @Test
    public void shouldFailOnWrongPassword(){
        final Response response = rule.getJerseyTest().target("/hello-world")
                .request(MediaType.APPLICATION_JSON_TYPE)
                .header("Authorization", "Basic " + BaseEncoding.base64().encode("user:wrong".getBytes()))
                .get();

        assertThat(response.getStatus()).isEqualTo(401);
    }
}