package com.vvs.roomReservations.model.service;

import com.vvs.roomReservations.exception.EmptyInput;
import com.vvs.roomReservations.exception.InvalidGuestID;
import com.vvs.roomReservations.exception.InvalidRoomId;
import com.vvs.roomReservations.model.dto.RoomReservation;
import com.vvs.roomReservations.data.entity.Guest;
import com.vvs.roomReservations.data.entity.Reservation;
import com.vvs.roomReservations.data.entity.Room;
import com.vvs.roomReservations.data.repository.GuestRepository;
import com.vvs.roomReservations.data.repository.ReservationRepository;
import com.vvs.roomReservations.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.util.*;
import java.util.stream.StreamSupport;

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
        Map<Long,RoomReservation> roomReservationMap = new HashMap<>();
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
                Optional<Guest> optionalGuest = this.guestRepository.findById(reservation.getGuestId());
                Guest guest=optionalGuest.get();
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

    public void addReservation(RoomReservation roomReservation) {
        try {
            this.reservationRepository.save(convertToReservation(roomReservation));
        }catch (EmptyInput | InvalidRoomId | InvalidGuestID emptyInput){
            emptyInput.printStackTrace();
        }
    }
    private Reservation convertToReservation(RoomReservation roomReservation) throws EmptyInput, InvalidRoomId, InvalidGuestID {
        if(roomReservation.getRoomId()==null || roomReservation.getGuestId() ==null){
            throw new EmptyInput("RoomID or GuestId are empty");
        }
        if(!isPresentRoom(roomReservation.getRoomId())){
            throw new InvalidRoomId("Invalid Room ID");
        }
        if(!isPresentGuest(roomReservation.getGuestId())){
            throw  new InvalidGuestID("Invalid GUEST ID");
        }
        Date date = new Date();
        return new Reservation(roomReservation.getRoomId(),roomReservation.getGuestId(), new java.sql.Date(date.getTime()));
    }
    private boolean isPresentRoom(Long roomId){
        Iterable<Room> reservations = roomRepository.findAll();
        List<Room> roomList =new ArrayList<>();
        reservations.forEach(roomList::add);
        Optional<Room> room=roomList.stream().filter(r->r.getRoomId()==roomId).findAny();
        if(room.isPresent()){
            return true;
        }
        return false;
    }
    private boolean isPresentGuest(Long guestId){
        Iterable<Guest> reservations = guestRepository.findAll();
        List<Guest> roomList =new ArrayList<>();
        reservations.forEach(roomList::add);
        Optional<Guest> room=roomList.stream().filter(r->r.getGuestId()==guestId).findAny();
        if(room.isPresent()){
            return true;
        }
        return false;
    }


}
