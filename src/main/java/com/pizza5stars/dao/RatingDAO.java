package com.pizza5stars.dao;

import com.pizza5stars.dao.mappers.RatingMapper;
import com.pizza5stars.representations.Address;
import com.pizza5stars.representations.Rating;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface RatingDAO {
    @GetGeneratedKeys
    @SqlUpdate("insert into rating (customerId, pizzaId, value) values (:customerId, :pizzaId, :value)")
    int createRating(@Bind("customerId") int customerId,
                     @Bind("pizzaId") int pizzaId,
                     @Bind("rating") double value);

    @Mapper(RatingMapper.class)
    @SqlQuery("select * from rating where customer_id = :customerId")
    List<Rating>getRatingsByCustomerId(@Bind("customerId") int customerId);


}
