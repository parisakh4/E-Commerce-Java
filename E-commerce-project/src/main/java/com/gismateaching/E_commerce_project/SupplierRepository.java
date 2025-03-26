package com.gismateaching.E_commerce_project;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
 //getting products of one supplier
    @Query("SELECT s FROM Supplier s LEFT JOIN FETCH s.products WHERE s.supplier_id = :supplier_id")
    Optional<Supplier> findSupplierWithProducts(@Param("supplier_id") Integer supplier_id);
}
