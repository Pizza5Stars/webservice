package com.pizza5stars;

import com.github.toastshaman.dropwizard.auth.jwt.JWTAuthFilter;
import com.github.toastshaman.dropwizard.auth.jwt.JsonWebTokenParser;
import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Verifier;
import com.github.toastshaman.dropwizard.auth.jwt.parser.DefaultJsonWebTokenParser;
import com.pizza5stars.authentication.JWTAuthenticator;
import com.pizza5stars.resources.*;
import io.dropwizard.Application;
import io.dropwizard.auth.AuthDynamicFeature;
import io.dropwizard.auth.AuthValueFactoryProvider;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;
import org.skife.jdbi.v2.DBI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.security.Principal;
import java.util.EnumSet;

import static org.eclipse.jetty.servlets.CrossOriginFilter.*;

public class App extends Application<Pizza5StarsConfiguration> {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public void initialize(Bootstrap<Pizza5StarsConfiguration> b) {}

    @Override
    public void run(Pizza5StarsConfiguration c, Environment e) throws Exception {
        LOGGER.info("Method App#run() called");

        //Create a DBI factory and build a JDBI instance
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(e, c.getDataSourceFactory(), "mysql");

        registerAuthenticator(c, e, jdbi);
        registerRoutes(c, e, jdbi);

        //Allow Cross-Origin-Resource-Sharing
        configureCORS(e);
    }

    private void configureCORS(Environment environment) {
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        FilterRegistration.Dynamic filter = environment.servlets().addFilter("CORSFilter", CrossOriginFilter.class);

        filter.addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, environment.getApplicationContext().getContextPath() + "*");
        filter.setInitParameter(ALLOWED_METHODS_PARAM, "GET,PUT,POST,DELETE,OPTIONS");
        filter.setInitParameter(ALLOWED_ORIGINS_PARAM, "*");
        filter.setInitParameter(ALLOWED_HEADERS_PARAM, "*");
        filter.setInitParameter(ALLOW_CREDENTIALS_PARAM, "true");
    }

    private void registerRoutes(Pizza5StarsConfiguration c, Environment e, DBI jdbi) throws Exception {
        //Add the resource to the environment
        e.jersey().register(new CustomerResource(jdbi, e.getValidator()));
        e.jersey().register(new AuthResource(jdbi, e.getValidator(), c.getJwtTokenSecret()));
        e.jersey().register(new IngredientResource(jdbi));
        e.jersey().register(new PizzaResource(jdbi, e.getValidator()));
        e.jersey().register(new AddressResource(jdbi, e.getValidator()));
        e.jersey().register(new OrderResource(jdbi, e.getValidator()));
    }

    private void registerAuthenticator(Pizza5StarsConfiguration c, Environment e, DBI jdbi) throws Exception {
        //register token authentication
        final JsonWebTokenParser tokenParser = new DefaultJsonWebTokenParser();
        final HmacSHA512Verifier tokenVerifier = new HmacSHA512Verifier(c.getJwtTokenSecret());
        e.jersey().register(new AuthDynamicFeature(
                new JWTAuthFilter.Builder<>()
                        .setTokenParser(tokenParser)
                        .setTokenVerifier(tokenVerifier)
                        .setRealm("realm")
                        .setPrefix("Bearer")
                        .setAuthenticator(new JWTAuthenticator(jdbi))
                        .buildAuthFilter()));
        e.jersey().register(new AuthValueFactoryProvider.Binder<>(Principal.class));
        e.jersey().register(RolesAllowedDynamicFeature.class);
    }

}
