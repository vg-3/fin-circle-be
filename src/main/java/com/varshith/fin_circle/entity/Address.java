package com.varshith.fin_circle.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String apartmentName;
    private String flatNumber;
    private String street;
    private String landmark;
    private String city;
    private String state;
    private Integer pinCode;

    @OneToOne(mappedBy = "address")
    @JsonBackReference
    private UserDetails userDetails;

}