package com.pizza5stars.dao;

import com.pizza5stars.dao.mappers.AddressMapper;
import com.pizza5stars.representations.Address;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface AddressDAO {
    @GetGeneratedKeys
    @SqlUpdate("insert into address (id, street, zipcode, housenumber, city, firstname, lastname, customer_id, phone) values (NULL, :street, :zipcode, :housenumber, :city, :firstName, :lastName, :customerId, :phone)")
    int createAddress(@Bind("street") String street,
                      @Bind("zipcode") String zipcode,
                      @Bind("housenumber") String housenumber,
                      @Bind("city") String city,
                      @Bind("firstName") String firstName,
                      @Bind("lastName") String lastName,
                      @Bind("customerId") int customerId,
                      @Bind("phone") String phone);

    @GetGeneratedKeys
    @SqlUpdate("insert into address (id, street, zipcode, housenumber, city, firstname, lastname, customer_id, phone) " +
            "values (NULL, :street, :zipcode, :housenumber, :city, :firstName, :lastName, NULL,  :phone)")
    int createAddressWithoutCustomer(@Bind("street") String street,
                                     @Bind("zipcode") String zipcode,
                                     @Bind("housenumber") String housenumber,
                                     @Bind("city") String city,
                                     @Bind("firstName") String firstName,
                                     @Bind("lastName") String lastName,
                                     @Bind("phone") String phone);

    @Mapper(AddressMapper.class)
    @SqlQuery("select * from address where customerId = :customerId")
    List<Address>getAddressesByCustomerId(@Bind("customerId") int customerId);


}
