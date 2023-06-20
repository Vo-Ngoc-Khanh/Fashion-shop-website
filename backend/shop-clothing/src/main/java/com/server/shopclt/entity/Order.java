package com.server.shopclt.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.server.shopclt.enums.StatusOrder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="tbl_orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(name="total_amount")
    private BigDecimal totalAmount;
    
    @Column(name="shipping_address")
    private String shippingAddress;
    
    @Enumerated(EnumType.STRING)
    private StatusOrder status;
    
    @Column(name = "created_at")
    private LocalDate createAt;
    
    @Column(name = "expected_delivery_date")
    private LocalDate ExpectedDeliveryDate;
    
    @Column(name="cancellation_reason")
    private String CancellationReason;
    
    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderDetails;
    
}
