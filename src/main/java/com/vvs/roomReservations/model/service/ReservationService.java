package com.vvs.roomReservations.model.service;

import com.vvs.roomReservations.model.dto.GuestReservation;
import com.vvs.roomReservations.model.dto.RoomReservation;
import com.vvs.roomReservations.data.entity.Guest;
import com.vvs.roomReservations.data.entity.Reservation;
import com.vvs.roomReservations.data.entity.Room;
import com.vvs.roomReservations.data.repository.GuestRepository;
import com.vvs.roomReservations.data.repository.ReservationRepository;
import com.vvs.roomReservations.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.*;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(
            RoomRepository roomRepository, GuestRepository guestRepository,
            ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationForDate(Date date){
        Iterable<Room>rooms = this.roomRepository.findAll();
        Map<Long,RoomReservation> roomReservationMap = new HashMap();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getRoomName());
            roomReservation.setRoomNumber(room.getRoomNUmber());
            roomReservationMap.put(room.getRoomId(),roomReservation);
        });
        Iterable<Reservation> reservations =
                this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());

        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for(Long id:roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(id));
        }
        return  roomReservations;
    }
    public List<GuestReservation> getGuests(){
        List<GuestReservation> guestsResult=new ArrayList<>();
        Iterable<Guest> guests=this.guestRepository.findAll();
        guests.forEach(guest -> {
            GuestReservation guestReservation = new GuestReservation();
            guestReservation.setEmail(guest.getEmailAddress());
            guestsResult.add(guestReservation);

        });
        return guestsResult;
    }

    public void addReservation(RoomReservation roomReservation){
        this.reservationRepository.save(convertToReservation(roomReservation));
    }
    private Reservation convertToReservation(RoomReservation roomReservation){
        Date date = new Date();
        return new Reservation(roomReservation.getRoomId(),roomReservation.getGuestId(), new java.sql.Date(date.getTime()));
    }

}
