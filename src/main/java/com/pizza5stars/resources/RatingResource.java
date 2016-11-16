package com.pizza5stars.resources;

import com.pizza5stars.dao.IngredientDAO;
import com.pizza5stars.dao.RatingDAO;
import com.pizza5stars.representations.Ingredient;
import com.pizza5stars.representations.Rating;
import org.skife.jdbi.v2.DBI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/rate")
@Produces(MediaType.APPLICATION_JSON)
public class RatingResource {

    private final RatingDAO ratingDAO;
    public RatingResource(DBI jdbi) {
        this.ratingDAO = jdbi.onDemand(RatingDAO.class);
    }

    @GET
    @Path("/{id}")
    public Response getRatingByPizzaId(@PathParam("id") int id) {
        int rating = ratingDAO.getRatingByPizzaId(id);
        Rating PizzaRating  = new Rating(id, rating);
        return Response.ok(PizzaRating).build();
    }

}
