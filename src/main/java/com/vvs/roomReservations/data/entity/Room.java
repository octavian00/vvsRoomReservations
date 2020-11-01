package com.vvs.roomReservations.data.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name="ROOM")
@Getter
public class Room {
    @Id
    @Column(name = "ROOM_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;

    @Column(name = "NAME")
    private String roomName;

    @Column(name = "ROOM_NUMBER")
    private String roomNUmber;

    @Column(name = "BED_INFO")
    private String bedInfo;
}
