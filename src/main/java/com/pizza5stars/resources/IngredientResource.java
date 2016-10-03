package com.pizza5stars.resources;

import com.pizza5stars.dao.IngredientDAO;
import com.pizza5stars.representations.Ingredient;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/ingredients")
@Produces(MediaType.APPLICATION_JSON)
public class IngredientResource {

    private final IngredientDAO ingredientDAO;
    public IngredientResource(DBI jdbi) {
        this.ingredientDAO = jdbi.onDemand(IngredientDAO.class);
    }

    @GET
    public Response getIngredients() {
        List<Ingredient> ingredients = ingredientDAO.getIngredients();
        return Response.ok(ingredients).build();
    }

}
