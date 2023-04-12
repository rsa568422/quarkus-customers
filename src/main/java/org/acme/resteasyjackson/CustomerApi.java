package org.acme.resteasyjackson;

import org.acme.entities.Customer;
import org.acme.repositories.CustomerRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerApi {

    @Inject
    private CustomerRepository repository;

    @GET
    public List<Customer> list() {
        return repository.list();
    }

    @GET
    @Path("/{id}")
    public Optional<Customer> findById(@PathParam("id") Long id) {
        return repository.findById(id);
    }

    @POST
    public Response add(Customer customer) {
        repository.create(customer);
        return Response.ok().build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, Customer customer) {
        Optional<Customer> optionalProduct = repository.findById(id);
        if (optionalProduct.isEmpty()) return Response.serverError().tag("Customer not found").build();
        customer.setId(optionalProduct.get().getId());
        repository.update(customer);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        repository.delete(id);
        return Response.noContent().build();
    }
}
