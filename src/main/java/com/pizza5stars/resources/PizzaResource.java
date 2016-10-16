package com.pizza5stars.resources;

import com.pizza5stars.dao.PizzaDAO;
import com.pizza5stars.dao.SizeDAO;
import com.pizza5stars.representations.Pizza;
import com.pizza5stars.representations.Size;
import org.skife.jdbi.v2.DBI;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Path("/pizza")
@Produces(MediaType.APPLICATION_JSON)
public class PizzaResource {

    private final PizzaDAO pizzaDAO;
    private final SizeDAO sizeDAO;
    private final Validator validator;

    public PizzaResource(DBI jdbi, Validator validator) {
        this.pizzaDAO = jdbi.onDemand(PizzaDAO.class);
        this.sizeDAO = jdbi.onDemand(SizeDAO.class);
        this.validator = validator;
    }

    @POST
    public Response createPizza(Pizza pizza) {
        Set<ConstraintViolation<Pizza>> violations = validator.validate(pizza);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Pizza> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {
            int pizzaId = pizzaDAO.createPizzaWithinTransaction(0, pizza);
            return Response.ok(pizzaId).build();
        }
    }

    @GET
    public Response getPizzasByIds(@QueryParam("ids") final List<Integer> ids) {
        List<Pizza> pizzas = pizzaDAO.getPizzasByIds(ids);
        return Response.ok(pizzas).build();
    }

    @GET
    @Path("/suggestions")
    public Response getSuggestions() {
        List<Pizza> pizzas = pizzaDAO.getPizzaSuggestions();
        return Response.ok(pizzas).build();
    }

    @GET
    @Path("/sizes")
    public Response getSizes() {
        List<Size> sizes = sizeDAO.getSizes();
        return Response.ok(sizes).build();
    }

}
