package com.gismateaching.E_commerce_project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table (name = "Order_Details")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_detail_id;
    /*@Column(name = "order_id", insertable = false, updatable = false)
    private Integer order_id;
    @Column(name = "product_id", insertable = false, updatable = false)
    private Integer product_id;*/
    private Integer quantity;
    private Double price_each;

    public OrderDetail() {}

    public OrderDetail(Integer order_detail_id, Integer quantity, Double price_each) {
        this.order_detail_id = order_detail_id;
        this.quantity = quantity;
        this.price_each = price_each;
    }

    //Defining relationships
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "product_id")
    private Product product;


    public Integer getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(Integer order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    /*public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }*/

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice_each() {
        return price_each;
    }

    public void setPrice_each(Double price_each) {
        this.price_each = price_each;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(order_detail_id, that.order_detail_id) && Objects.equals(order, that.order)
                && Objects.equals(product, that.product) && Objects.equals(quantity, that.quantity) && Objects.equals(price_each, that.price_each);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_detail_id, order, product, quantity, price_each);
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "order_detail_id=" + order_detail_id +
                ", order=" + (order != null ? order.getOrder_id() : "null") +
                ", product=" + (product != null ? product.getProduct_id() : "null") +
                ", quantity=" + quantity +
                ", price_each=" + price_each +
                '}';
    }
}
