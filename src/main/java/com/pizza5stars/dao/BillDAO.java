package com.pizza5stars.dao;

import com.pizza5stars.dao.mappers.BillMapper;
import com.pizza5stars.representations.Bill;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface BillDAO {
    @SqlUpdate("insert into bill (nr, total, date, order_nr, address_id) values (NULL, :total, CURRENT_TIMESTAMP, :orderNr, :addressId)")
    int insertBill(
            @Bind("total") double total,
            @Bind("orderNr") int orderNr,
            @Bind("addressId") int addressId);

    @Mapper(BillMapper.class)
    @SqlQuery(
            "select bill.* , address.*, `order`.*, pizza.name " +
            ",group_concat(pizza.name separator ',') as pizzas " +
            "from bill " +
            "join address on bill.address_id = address.id " +
            "join `order` on bill.order_nr = `order`.nr " +
            "join pizza_order on pizza_order.order_nr = `order`.nr "+
            "join pizza on pizza_order.pizza_id = pizza.id " +
            "where `order`.customer_id = :userId " +
            "group by bill.nr")
    List<Bill> getBillsByCustomerId(@Bind("customerId") int customerId);
}
