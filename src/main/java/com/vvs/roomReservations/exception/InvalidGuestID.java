package com.vvs.roomReservations.exception;

public class InvalidGuestID extends Exception {
    public InvalidGuestID(String invalid_guest_id) {
        super(invalid_guest_id);
    }
}
