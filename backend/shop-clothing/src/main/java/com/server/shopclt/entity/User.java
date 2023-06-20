package com.server.shopclt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="tbl_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    private String fullname;
    
    private String email;
    
    private String password;
    
    private String phoneNumber;
    
    private String address;
    
    private Boolean status;
    
    @ManyToOne 
    @JoinColumn(name = "role_id")
    private Role role;
    


}

