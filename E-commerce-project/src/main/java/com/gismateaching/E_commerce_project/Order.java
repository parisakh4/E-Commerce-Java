package com.gismateaching.E_commerce_project;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.LocalDate;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;
    private LocalDate order_date;
    private Double total_amount;
    private String o_status;
    private Integer address_id;
    private LocalDateTime changed_at;


    public Order() {
    }

    public Order(LocalDate order_date, Double total_amount, Integer customer_id, Integer address_id) {
        this.order_date = order_date;
        this.total_amount = total_amount;
        this.o_status = o_status;
        this.address_id = address_id;
        this.changed_at = LocalDateTime.now();
    }


    //defining the relations
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderDetail> order_details = new ArrayList<>();


    //Getters and Setters
    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public LocalDate getOrder_date() {
        return order_date;
    }

    public void setOrder_date(LocalDate order_date) {
        this.order_date = order_date;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public String getO_status() {
        return o_status;
    }

    public void setO_status(String o_status) {
        this.o_status = o_status;
    }


    public void setCustomer(Customer customer) { this.customer = customer; }
    public Customer getCustomer() { return customer; }


    public Integer getAddress_id() {
        return address_id;
    }

    public void setAddress_id(Integer address_id) {
        this.address_id = address_id;
    }

    public LocalDateTime getChanged_at() {return changed_at;}

    public void setChanged_at(LocalDateTime changed_at) {
        this.changed_at = changed_at;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(order_id, order.order_id) && Objects.equals(order_date, order.order_date) && Objects.equals(total_amount, order.total_amount) && Objects.equals(o_status, order.o_status) && (customer != null ? customer.equals(order.customer) : order.customer == null) && Objects.equals(address_id, order.address_id) && Objects.equals(changed_at, order.changed_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order_id, order_date, total_amount, o_status,customer != null ? customer.getCustomer_id() : null , address_id, changed_at);
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", order_date=" + order_date +
                ", total_amount=" + total_amount +
                ", o_status='" + o_status + '\'' +
                ", customer_id=" + (customer != null ? customer.getCustomer_id() : "null") +
                ", address_id=" + address_id +
                ", changed_at=" + changed_at +
                '}';
    }

}



