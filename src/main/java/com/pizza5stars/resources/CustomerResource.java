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
    private final OrderDAO orderDAO;
    private final Validator validator;
    private final ReceiptDAO receiptDAO;
    private final RatingDAO ratingDAO;

    public CustomerResource(DBI jdbi, Validator validator) {
        this.customerDAO = jdbi.onDemand(CustomerDAO.class);
        this.addressDAO = jdbi.onDemand(AddressDAO.class);
        this.pizzaDAO = jdbi.onDemand(PizzaDAO.class);
        this.orderDAO = jdbi.onDemand(OrderDAO.class);
        this.receiptDAO = jdbi.onDemand(ReceiptDAO.class);
        this.ratingDAO = jdbi.onDemand(RatingDAO.class);
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

    @POST
    @Path("/order")
    public Response createOrder(@Auth Principal customerPrincipal, Order order) {
        Set<ConstraintViolation<Order>> violations = validator.validate(order);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Order> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {

            //create order
            int customerId = ((Customer) customerPrincipal).getId();
            int orderId = orderDAO.createOrderWithinTransaction(order, customerId);

            //create bill
            double total = orderDAO.getPriceOfOrderByNr(orderId);
            int billId = receiptDAO.insertReceipt(total, orderId, order.getAddressId());

            return Response.ok(billId).build();
        }
    }

    @GET
    @Path("/receipts")
    public Response getBillsFromCustomer(@Auth Principal userPrincipal) throws URISyntaxException {
        int customerId = ((Customer) userPrincipal).getId();
        List<Receipt> bills = receiptDAO.getReceiptByCustomerId(customerId);
        return Response.ok(bills).build();
    }

    @POST
    @Path("/rate")
    public Response createRating(@Auth Principal customerPrincipal, Rating rating) {
        Set<ConstraintViolation<Rating>> violations = validator.validate(rating);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Rating> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {

            //create rating
            int customerId = ((Customer) customerPrincipal).getId();
            int ratingId = ratingDAO.createRating(customerId,rating.getPizzaId(), rating.getRating());

            return Response.ok(ratingId).build();
        }
    }
}
