package com.pizza5stars.dao;

import com.pizza5stars.dao.mappers.ReceiptMapper;
import com.pizza5stars.representations.Receipt;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.List;

public interface ReceiptDAO {
    @SqlUpdate("insert into receipt (nr, total, date, order_nr, address_id) values (NULL, :total, CURRENT_TIMESTAMP, :orderNr, :addressId)")
    int insertReceipt(
            @Bind("total") double total,
            @Bind("orderNr") int orderNr,
            @Bind("addressId") int addressId);

    @Mapper(ReceiptMapper.class)
    @SqlQuery(
            "select receipt.* , address.*, `order`.* " +
                    ",group_concat(pizza.name separator ',') as pizzas " +
                    "from receipt " +
                    "join address on receipt.address_id = address.id " +
                    "join `order` on receipt.order_nr = `order`.nr " +
                    "join pizza_order on pizza_order.order_nr = `order`.nr "+
                    "join pizza on pizza_order.pizza_id = pizza.id " +
                    "where `order`.customer_id = :customerId " +
                    "group by receipt.nr")
    List<Receipt> getReceiptsByCustomerId(@Bind("customerId") int customerId);
}
