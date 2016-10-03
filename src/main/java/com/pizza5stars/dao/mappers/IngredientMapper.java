package com.pizza5stars.dao.mappers;

import com.pizza5stars.representations.Ingredient;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IngredientMapper implements ResultSetMapper<Ingredient> {
    public Ingredient map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {
        return new Ingredient(
                r.getString("name"), r.getFloat("price"), r.getString("category_name"));
    }
}
