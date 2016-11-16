package com.pizza5stars.dao.mappers;

import com.pizza5stars.representations.Rating;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingMapper implements ResultSetMapper<Rating> {
    public Rating map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {

        return new Rating(
                r.getInt("pizzaId"),
                r.getDouble("rating"));
    }
}
