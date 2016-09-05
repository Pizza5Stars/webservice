package com.pizza5stars.dao;

import com.pizza5stars.dao.mappers.CustomerMapper;
import com.pizza5stars.representations.Customer;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

public interface CustomerDAO {
    @Mapper(CustomerMapper.class)
    @SqlQuery("select * from customer where id = :id")
    Customer getCustomerById(@Bind("id") int id);

    @Mapper(CustomerMapper.class)
    @SqlQuery("select * from customer where email = :email")
    Customer getCustomerByMail(@Bind("email") String email);

    @SqlQuery("select count(*) from customer where email = :email")
    int getCountOfCustomersByMail(@Bind("email") String email);

    @GetGeneratedKeys
    @SqlUpdate("insert into customer (id, email, firstName, lastName, password, deleted) values (NULL, :email, :firstName, :lastName, :password, FALSE)")
    int createCustomer(@Bind("email") String email, @Bind("firstName") String firstName, @Bind("lastName") String lastName, @Bind("password") String password);
}
