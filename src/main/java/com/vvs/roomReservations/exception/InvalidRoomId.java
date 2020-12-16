package com.vvs.roomReservations.exception;

public class InvalidRoomId extends Exception {
    public InvalidRoomId(String invalid_room_id) {
        super((invalid_room_id));
    }
}
