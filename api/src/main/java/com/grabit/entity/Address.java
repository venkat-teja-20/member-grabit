package com.grabit.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Address")
public class Address extends AuditDetails<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "house_number",nullable = false,length = 10)
    private String houseOrFlatNumber;

    @Column(name = "address_line",nullable = false,length = 50)
    private String addressLine;

    @Column(name = "land_mark",length = 25)
    private String landMark;

    @Column(name = "pin_code",nullable = false,length = 6)
    private String pinCode;

    @Column(name = "latitude",nullable = false)
    private String latitude;

    @Column(name = "longitude",nullable = false)
    private String longitude;

    @Column(name = "address_type",nullable = false)
    private String addressType;

    @ManyToOne
    @JoinColumn(name = "member_id",nullable = false)
    @JsonIgnore
    private Member member;
}
