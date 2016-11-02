package com.pizza5stars.resources;

import com.pizza5stars.dao.OrderDAO;
import com.pizza5stars.representations.Order;
import org.skife.jdbi.v2.DBI;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

@Path("/order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private final OrderDAO orderDAO;
    private final Validator validator;

    public OrderResource(DBI jdbi, Validator validator) {
        this.orderDAO = jdbi.onDemand(OrderDAO.class);
        this.validator = validator;
    }

    @POST
    public Response createOrder(Order order) throws URISyntaxException {
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
            int orderId = orderDAO.createOrderWithinTransaction(order, 0);
            return Response.ok(orderId).build();
        }
    }
}
