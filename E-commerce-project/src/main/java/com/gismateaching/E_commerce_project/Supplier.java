package com.gismateaching.E_commerce_project;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Suppliers")
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplier_id;
    private String supplier_name;
    private String supplier_phone;
    private String supplier_email;

    public Supplier() {}
    public Supplier(String supplier_name, String supplier_phone, String supplier_email) {}

    //Defining relationships
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplier_name() {
        return supplier_name;
    }

    public void setSupplier_name(String supplier_name) {
        this.supplier_name = supplier_name;
    }

    public String getSupplier_phone() {
        return supplier_phone;
    }

    public void setSupplier_phone(String supplier_phone) {
        this.supplier_phone = supplier_phone;
    }

    public String getSupplier_email() {
        return supplier_email;
    }

    public void setSupplier_email(String supplier_email) {
        this.supplier_email = supplier_email;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(supplier_id, supplier.supplier_id) && Objects.equals(supplier_name, supplier.supplier_name) && Objects.equals(supplier_phone, supplier.supplier_phone) && Objects.equals(supplier_email, supplier.supplier_email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier_id, supplier_name, supplier_phone, supplier_email);
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "supplier_id=" + supplier_id +
                ", supplier_name='" + supplier_name + '\'' +
                ", supplier_phone='" + supplier_phone + '\'' +
                ", supplier_email='" + supplier_email + '\'' +
                '}';
    }
}
