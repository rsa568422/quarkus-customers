package org.acme.repositories;

import org.acme.entities.Customer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class CustomerRepository {

    @Inject
    EntityManager em;

    @Transactional
    public Optional<Customer> findById(Long id) {
        Object customer = em.createQuery("select c from Customer c where p.id = :id")
                .setParameter("id", id)
                .getSingleResult();
        if (Objects.isNull(customer)) return Optional.empty();
        return Optional.of((Customer) customer);
    }

    @Transactional
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Transactional
    public void delete(Long id) {
        em.createQuery("delete from Customer c where c.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Transactional
    public List<Customer> list() {
        List<Customer> customers = new LinkedList<>();
        em.createQuery("select c from Customer c")
                .getResultList()
                .listIterator()
                .forEachRemaining(o -> customers.add((Customer) o));
        return customers;
    }

    @Transactional
    public void update(Customer customer) {
        em.createQuery("update Customer c set c.name = :name, c.description = :description where c.id = :id")
                .setParameter("name", customer.getName())
                .setParameter("description", customer.getDescription())
                .setParameter("id", customer.getId())
                .executeUpdate();
    }
}
