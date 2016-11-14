package com.pizza5stars.dao;

import com.pizza5stars.dao.mappers.IngredientMapper;
import com.pizza5stars.representations.Ingredient;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface IngredientDAO {
    @Mapper(IngredientMapper.class)
    @SqlQuery("select * from ingredient")
    List<Ingredient> getIngredients();

    @SqlUpdate("insert into ingredient (name, price) values (:name, :price)")
    void createIngredient(@Bind("name") String name, @Bind("price") double price);
}
