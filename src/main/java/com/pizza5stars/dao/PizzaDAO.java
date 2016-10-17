package com.pizza5stars.dao;

import com.pizza5stars.dao.mappers.PizzaMapper;
import com.pizza5stars.representations.Pizza;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import org.skife.jdbi.v2.unstable.BindIn;

import java.util.List;

@UseStringTemplate3StatementLocator
public abstract class PizzaDAO {
    @GetGeneratedKeys
    @SqlUpdate("insert into pizza (id, customer_id, name, size_name, is_default) values (NULL, :customerId, :name, :sizeName, FALSE)")
    public abstract int createPizza(
            @Bind("customerId") int customerId,
            @Bind("name") String name,
            @Bind("sizeName") String sizeName);

    //create Pizza without customerId parameter
    @GetGeneratedKeys
    @SqlUpdate("insert into pizza (id, customer_id, name, size_name, is_default) values (NULL, NULL, :name, :sizeName, FALSE)")
    public abstract int createPizzaWithoutCustomer(
            @Bind("name") String name,
            @Bind("sizeName") String sizeName);


    @SqlBatch("insert into pizza_ingredient (pizza_id, ingredient_name) values (:pizzaId, :ingredients)")
    public abstract void addIngredientsToPizza(
            @Bind("ingredients") List<String> ingredients,
            @Bind("pizzaId") int pizzaId);

    //doesn't really delete pizza from db, just sets the customer_id to NULL
    @SqlUpdate("update pizza set customer_id = NULL where id = :id")
    public abstract void deletePizza(@Bind("id") int id);

    @SqlQuery("select count(*) from pizza where customer_id = :customerId and id = :pizzaId")
    public abstract int getCountOfPizzasByUserIdAndPizzaId(@Bind("customerId") int userId, @Bind("pizzaId") int pizzaId);


    /*
    SQL query transforms joined tables into format that facilitates creating objects in the PizzaMapper.

    example:
    table after simple join:
        id  customer_id name        price   size    ingredient
        4	1	        pizzaname	0.00	xxl     Pineapple
        4	1	        pizzaname	0.00	xxl	    Swiss Cheese
        4	1	        pizzaname	0.00	xxl	    Mango

    table after "group_concat" and "group by":
       id  customer_id  name        price   size    ingredient
        4	1	        pizzaname	0.00	xxl	    Pineapple,Swiss Cheese,Mango
     */
    @Mapper(PizzaMapper.class)
    @SqlQuery("select pizza.id, pizza.name, pizza.size_name " +
            ",group_concat(ingredient.name separator ',') as ingredients " +
            "from pizza_ingredient " +
            "join pizza on pizza_ingredient.pizza_id = pizza.id " +
            "join ingredient on pizza_ingredient.ingredient_name = ingredient.name " +
            "where pizza.customer_id = :customer_id " +
            "group by pizza.id")
    public abstract List<Pizza> getPizzasFromUserId(@Bind("customer_id") int customerId);

    @Mapper(PizzaMapper.class)
    @SqlQuery("select pizza.id, pizza.name, pizza.size_name " +
            ",group_concat(ingredient.name separator ',') as ingredients " +
            "from pizza_ingredient " +
            "join pizza on pizza_ingredient.pizza_id = pizza.id " +
            "join ingredient on pizza_ingredient.ingredient_name = ingredient.name " +
            "where is_default = TRUE " +
            "group by pizza.id")
    public abstract List<Pizza> getPizzaSuggestions();

    @Mapper(PizzaMapper.class)
    @SqlQuery("select pizza.id, pizza.name, pizza.size_name " +
            ",group_concat(ingredient.name separator ',') as ingredients " +
            "from pizza_ingredient " +
            "join pizza on pizza_ingredient.pizza_id = pizza.id " +
            "join ingredient on pizza_ingredient.ingredient_name = ingredient.name " +
            "where id in (<ids>) " +
            "group by pizza.id")
    public abstract List<Pizza> getPizzasByIds(@BindIn("ids") List<Integer> ids);

    @Transaction
    public int createPizzaWithinTransaction(int customerId, Pizza pizza) {
        int pizzaId = customerId > 0 ?
                createPizza(customerId, pizza.getName(), pizza.getSizeName()) :
                createPizzaWithoutCustomer(pizza.getName(), pizza.getSizeName());

        addIngredientsToPizza(pizza.getIngredients(), pizzaId);

        return pizzaId;
    }
}
