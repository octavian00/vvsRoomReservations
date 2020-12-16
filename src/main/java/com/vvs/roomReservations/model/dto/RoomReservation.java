package com.vvs.roomReservations.model.dto;

import lombok.Data;

import lombok.experimental.Accessors;

import java.util.Date;
@Data
@Accessors
public class RoomReservation {
    private Long roomId;
    private Long guestId;
    private String roomName;
    private String roomNumber;
    private String firstName;
    private String lastName;
    private Date date;
}
