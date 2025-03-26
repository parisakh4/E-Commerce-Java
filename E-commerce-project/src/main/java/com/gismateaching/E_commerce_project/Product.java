package com.gismateaching.E_commerce_project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.LocalDate;

@Entity
@Table(name = "Products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id;
    private String product_name;
    private String category;
    private Double price;
    private Integer stock;
    @Column(name = "supplier_id", insertable = false, updatable = false)
    private Integer supplier_id;

    public Product() {}

    public Product(Integer product_id, String product_name, String category, Double price, Integer stock, Integer supplier_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    //Defining relationships
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderDetail> order_details = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    @JsonBackReference
    private Supplier supplier;


    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(Integer supplier_id) {
        this.supplier_id = supplier_id;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(product_id, product.product_id) && Objects.equals(product_name, product.product_name) && Objects.equals(category, product.category) && Objects.equals(price, product.price) && Objects.equals(stock, product.stock);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_id, product_name, category, price, stock);
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", product_name='" + product_name + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                '}';
    }
}
