package com.pizza5stars.dao.mappers;

import com.pizza5stars.representations.Customer;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerMapper implements ResultSetMapper<Customer> {
    public Customer map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {
        return new Customer(
                r.getInt("id"), r.getString("email"), r.getString("firstname"),
                r.getString("lastname"), r.getString("password"));
    }
}