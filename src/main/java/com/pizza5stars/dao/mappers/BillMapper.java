package com.pizza5stars.dao.mappers;

import com.pizza5stars.representations.Address;
import com.pizza5stars.representations.Bill;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class BillMapper implements ResultSetMapper<Bill> {
    public Bill map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {

        Address address = new Address(
                r.getInt("address_id"),
                r.getString("street"),
                r.getString("zipcode"),
                r.getString("housenumber"),
                r.getString("city"),
                r.getString("firstname"),
                r.getString("lastname"),
                r.getString("phone"));

        String pizza = r.getString("pizzas");
        ArrayList<String> pizzaNames = new ArrayList<String>(Arrays.asList(pizza.split("\\s*,\\s*")));

        return new Bill(r.getInt("nr"), r.getDouble("total"), r.getInt("order_nr"), address, pizzaNames);
    }
}
