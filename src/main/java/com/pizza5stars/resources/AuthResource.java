package com.pizza5stars.resources;

import com.github.toastshaman.dropwizard.auth.jwt.hmac.HmacSHA512Signer;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebToken;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenClaim;
import com.github.toastshaman.dropwizard.auth.jwt.model.JsonWebTokenHeader;
import com.pizza5stars.authentication.PasswordEncrypter;
import com.pizza5stars.dao.CustomerDAO;
import com.pizza5stars.representations.AuthInfo;
import com.pizza5stars.representations.Credentials;
import com.pizza5stars.representations.Customer;
import io.dropwizard.auth.Auth;
import org.joda.time.DateTime;
import org.skife.jdbi.v2.DBI;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.singletonMap;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {
    private final byte[] tokenSecret;
    private final CustomerDAO customerDAO;
    private final Validator validator;

    public AuthResource(DBI jdbi, Validator validator, byte[] tokenSecret) {
        this.customerDAO = jdbi.onDemand(CustomerDAO.class);
        this.validator = validator;
        this.tokenSecret = tokenSecret;
    }

    @POST
    @Path("/login")
    public Response login(Credentials credentials) {
        Set<ConstraintViolation<Credentials>> violations = validator.validate(credentials);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Credentials> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {

            Customer customer = customerDAO.getCustomerByMail(credentials.getEmail());

            if (customer != null) {
                try {
                    if (!PasswordEncrypter.check(credentials.getPassword(), customer.getPassword())) {
                        return Response
                                .status(Response.Status.UNAUTHORIZED)
                                .build();
                    }

                } catch (Exception e) {
                    System.out.println(e.getMessage());
                    return Response
                            .status(Response.Status.BAD_REQUEST)
                            .build();
                }
            } else {
                return Response
                        .status(Response.Status.BAD_REQUEST)
                        .build();
            }

            final HmacSHA512Signer signer = new HmacSHA512Signer(tokenSecret);
            final JsonWebToken token = JsonWebToken.builder()
                    .header(JsonWebTokenHeader.HS512())
                    .claim(JsonWebTokenClaim.builder()
                            .subject("good-guy")
                            .param("id", customer.getId())
                            .param("email", customer.getEmail())
                            .issuedAt(DateTime.now())
                            .build())
                    .build();
            final String signedToken = signer.sign(token);

            Map tokenMap = singletonMap("token", signedToken);

            return Response.ok(new AuthInfo(customer.getFirstName(), customer.getLastName(), customer.getEmail(), tokenMap)).build();
        }
    }

    @GET
    @Path("/authenticate")
    public Response authenticate(@Auth Principal userPrincipal) {
        Customer customer = (Customer) userPrincipal;
        return Response.ok(customer).build();
    }
}

