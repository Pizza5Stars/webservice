package com.pizza5stars.dao.mappers;

import com.pizza5stars.representations.Address;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressMapper implements ResultSetMapper<Address> {
    public Address map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {

        return new Address(
                r.getInt("id"),
                r.getString("street"),
                r.getString("zipcode"),
                r.getString("housenumber"),
                r.getString("city"),
                r.getString("firstname"),
                r.getString("lastname"),
                r.getString("phone"));
    }
}
