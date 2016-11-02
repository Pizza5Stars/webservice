package com.pizza5stars.dao;

import com.pizza5stars.representations.Order;
import org.skife.jdbi.v2.sqlobject.*;

import java.util.List;

public abstract class OrderDAO {
    @GetGeneratedKeys
    @SqlUpdate("insert into `order` (nr, date, customer_id) values (NULL, CURRENT_TIMESTAMP, :customerId)")
    public abstract int insertOrder(
            @Bind("customerId") int customerId);

    @GetGeneratedKeys
    @SqlUpdate("insert into `order` (nr, date, customer_id) values (NULL, CURRENT_TIMESTAMP, NULL)")
    public abstract int insertOrderWithoutCustomer();

    @SqlBatch("insert into pizza_order (pizza_id, order_nr) values (:pizzaIds, :orderNr)")
    public abstract void addPizzasToOrder(
            @Bind("pizzaIds") List<Integer> pizzaIds,
            @Bind("orderNr") int orderNr);

    @SqlQuery("select sum(price * price_factor) from pizza_order " +
            "join pizza on pizza_order.pizza_id = pizza.id " +
            "join pizza_ingredient on pizza.id = pizza_ingredient.pizza_id " +
            "join ingredient on pizza_ingredient.ingredient_name = ingredient.name " +
            "join size on pizza.size_name = size.name " +
            "where pizza_order.order_nr = :orderNr")
    public abstract double getPriceOfOrderByNr(@Bind("orderNr") int orderNr);

    @Transaction
    public int createOrderWithinTransaction(Order order, int customerId) {
        int orderNr = customerId > 0 ? insertOrder(customerId) : insertOrderWithoutCustomer();
        addPizzasToOrder(order.getPizzaIds(), orderNr);
        return orderNr;
    }
}
