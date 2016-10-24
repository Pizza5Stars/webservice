package com.pizza5stars.resources;

import com.pizza5stars.dao.AddressDAO;
import com.pizza5stars.representations.Address;
import org.skife.jdbi.v2.DBI;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Set;

@Path("/address")
@Produces(MediaType.APPLICATION_JSON)
public class AddressResource {

    private final AddressDAO addressDAO;
    private final Validator validator;


    public AddressResource(DBI jdbi, Validator validator) {
        this.addressDAO = jdbi.onDemand(AddressDAO.class);
        this.validator = validator;
    }

    @POST
    public Response createAddress(Address address) throws URISyntaxException {
        Set<ConstraintViolation<Address>> violations = validator.validate(address);
        if (violations.size() > 0) {
            ArrayList<String> validationMessages = new ArrayList<String>();
            for (ConstraintViolation<Address> violation : violations) {
                validationMessages.add(violation.getPropertyPath().toString() + ": " + violation.getMessage());
            }
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(validationMessages)
                    .build();
        } else {
            int addressId = addressDAO.createAddressWithoutCustomer(
                    address.getStreet(),
                    address.getZipcode(),
                    address.getHousenumber(),
                    address.getCity(),
                    address.getFirstname(),
                    address.getLastname(),
                    address.getPhone());
            return Response.ok(addressId).build();
        }
    }
}
