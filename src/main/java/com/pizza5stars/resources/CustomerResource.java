package com.pizza5stars.resources;
import com.pizza5stars.authentication.PasswordEncrypter;
import com.pizza5stars.dao.*;
import com.pizza5stars.representations.*;
import io.dropwizard.auth.Auth;
import org.skife.jdbi.v2.DBI;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {
    private final CustomerDAO customerDAO;
    private final AddressDAO addressDAO;
    private final PizzaDAO pizzaDAO;
    private final Validator validator;

    public CustomerResource(DBI jdbi, Validator validator) {
        this.customerDAO = jdbi.onDemand(CustomerDAO.class);
        this.addressDAO = jdbi.onDemand(AddressDAO.class);
        this.pizzaDAO = jdbi.onDemand(PizzaDAO.class);
        this.validator = validator;
    }

    @POST
    public Response createCustomer(Customer customer) throws URISyntaxException {
        // Validate the customers' data
        Set<ConstraintViolation<Customer>> violations = validator.validate(customer);
        // Are there any constraint violations?
        if (violations.size() > 0) {
            // Validation errors occurred
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Customer> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {

            //check if email already exists
            if (customerDAO.getCountOfCustomersByMail(customer.getEmail()) > 0 ) {
                return Response
                        .status(Status.BAD_REQUEST)
                        .build();
            }

            String password = customer.getPassword();
            //salt and hash password
            try {
                password = PasswordEncrypter.getSaltedHash(password);
            } catch (Exception e) {
                return Response
                        .status(Status.BAD_REQUEST)
                        .build();
            }

            // OK, no validation errors
            // Store customer
            int customerId = customerDAO.createCustomer(customer.getEmail(), customer.getFirstName(),
                    customer.getLastName(), password);
            return Response.ok(customerId).build();
        }
    }

    @POST
    @Path("/address")
    public Response addAddressToCustomer(@Auth Principal customerPrincipal, Address address) throws URISyntaxException {
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Address> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {
            int customerId = ((Customer) customerPrincipal).getId();
            int addressId = addressDAO.createAddress(
                    address.getStreet(),
                    address.getZipcode(),
                    address.getHousenumber(),
                    address.getCity(),
                    address.getFirstname(),
                    address.getLastname(),
                    customerId,
                    address.getPhone());
            return Response.ok(addressId).build();
        }
    }


    @GET
    @Path("/addresses")
    public Response getAddressesFromUser(@Auth Principal customerPrincipal) throws URISyntaxException {
        int customerId = ((Customer) customerPrincipal).getId();
        List<Address> addresses = addressDAO.getAddressesByCustomerId(customerId);
        return Response.ok(addresses).build();
    }
}
