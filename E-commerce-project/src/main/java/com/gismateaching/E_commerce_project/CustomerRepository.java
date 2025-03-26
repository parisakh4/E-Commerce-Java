package com.gismateaching.E_commerce_project;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //get customers order by id
    @EntityGraph(attributePaths = "orders")
    Optional<Customer> findById(Integer customer_id);
}
