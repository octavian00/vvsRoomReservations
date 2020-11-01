package com.vvs.roomReservations.data.entity;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name="RESERVATION")
@Getter
public class Reservation {
    @Id
    @Column(name="RESERVATION_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reservationId;
    @Column(name="ROOM_ID")
    private long roomId;
    @Column(name="GUEST_ID")
    private long guestId;
    @Column(name="RES_DATE")
    private Date reservationDate;

    public Reservation(long reservationId, long roomId, long guestId, Date reservationDate) {
        this.reservationId = reservationId;
        this.roomId = roomId;
        this.guestId = guestId;
        this.reservationDate = reservationDate;
    }

    public Reservation(long roomId, long guestId, Date reservationDate) {
        this.roomId = roomId;
        this.guestId = guestId;
        this.reservationDate = reservationDate;
    }
    public Reservation(){}
}
