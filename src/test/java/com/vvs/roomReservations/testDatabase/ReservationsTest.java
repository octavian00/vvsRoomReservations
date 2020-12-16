package com.vvs.roomReservations.testDatabase;

import com.vvs.roomReservations.data.entity.Reservation;
import com.vvs.roomReservations.data.repository.ReservationRepository;
import com.vvs.roomReservations.exception.EmptyInput;
import com.vvs.roomReservations.exception.InvalidGuestID;
import com.vvs.roomReservations.exception.InvalidRoomId;
import com.vvs.roomReservations.model.dto.RoomReservation;
import com.vvs.roomReservations.model.service.ReservationService;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ReservationsTest {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationService reservationService;

    @Test
    public void testconvertReservationsWtihNotExistGuestId(){
        reservationRepository.deleteAll();
        Date date=new Date();
        RoomReservation r=new RoomReservation();
        r.setRoomId(1L);
        r.setGuestId(1001L);
        r.setDate(date);
        assertThrows(InvalidGuestID.class, ()->reservationService.convertToReservation(r));
    }

    @Test
    public void testconvertReservationsWtihNotExistRoomId(){
        reservationRepository.deleteAll();
        Date date=new Date();
        RoomReservation r=new RoomReservation();
        r.setRoomId(101L);
        r.setGuestId(1L);
        r.setDate(date);
        assertThrows(InvalidRoomId.class, ()->reservationService.convertToReservation(r));
    }
    @Test
    public void testconvertReservationsWtihEmptyRoomId(){
        reservationRepository.deleteAll();
        Date date=new Date();
        RoomReservation r=new RoomReservation();
        r.setGuestId(1L);
        r.setDate(date);
        assertThrows(EmptyInput.class, ()->reservationService.convertToReservation(r));
    }

    @Test
    public void testconvertReservationsWtihEmptyGuestId(){
        reservationRepository.deleteAll();
        Date date=new Date();
        RoomReservation r=new RoomReservation();
        r.setRoomId(1L);
        r.setDate(date);
        assertThrows(EmptyInput.class, ()->reservationService.convertToReservation(r));
    }

    @Test
    public void testconvertReservationsWtihEmptyGuestIdAndRoomId(){
        reservationRepository.deleteAll();
        Date date=new Date();
        RoomReservation r=new RoomReservation();
        r.setDate(date);
        assertThrows(EmptyInput.class, ()->reservationService.convertToReservation(r));
    }
    @Test
    public void testaddWithSucces(){
        reservationRepository.deleteAll();
        Date date=new Date();
        RoomReservation r=new RoomReservation();
        r.setRoomId(1L);
        r.setGuestId(1L);
        r.setDate(date);
        reservationService.addReservation(r);
        List<Reservation> reservationListl=new ArrayList<>();
        Iterable<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(reservationListl::add);
        assertEquals(1,reservationListl.size());
        assertEquals(r.getGuestId(),reservationListl.get(0).getGuestId());
        assertEquals(r.getRoomId(),reservationListl.get(0).getRoomId());
    }
    @Test
    public void testFailAdd(){
        reservationRepository.deleteAll();
        Date date=new Date();
        RoomReservation r=new RoomReservation();
        r.setRoomId(1L);
        r.setGuestId(100001L);
        r.setDate(date);
        reservationService.addReservation(r);
        List<Reservation> reservationListl=new ArrayList<>();
        Iterable<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(reservationListl::add);
        assertEquals(0,reservationListl.size());
    }

    @Test
    public void getReservationsSucces(){
        assertEquals(28,reservationService.getRoomReservationForDate(new Date()).size());
    }

}
