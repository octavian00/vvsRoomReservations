package com.vvs.roomReservations.data.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GUEST")
@Getter
public class Guest {
    @Id
    @Column(name="GUEST_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long guestId;
    @Column(name="FIRST_NAME")
    private String firstName;
    @Column(name="LAST_NAME")
    private String lastName;
    @Column(name="EMAIL_ADDRESS")
    private String emailAddress;
    @Column(name="ADDRESS")
    private String address;
    @Column(name="COUNTRY")
    private String country;
    @Column(name="STATE")
    private String state;
    @Column(name="PHONE_NUMBER")
    private String phoneNumber;
}
