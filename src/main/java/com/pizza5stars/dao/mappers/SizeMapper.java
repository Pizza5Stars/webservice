package com.pizza5stars.dao.mappers;

import com.pizza5stars.representations.Size;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SizeMapper implements ResultSetMapper<Size> {
    public Size map(int index, ResultSet r, StatementContext ctx)
            throws SQLException {
        return new Size(
                r.getString("name"), r.getInt("size"), r.getDouble("price_factor"));
    }
}