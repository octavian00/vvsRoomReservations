package com.vvs.roomReservations.testDatabase;

import com.vvs.roomReservations.data.entity.Reservation;
import com.vvs.roomReservations.data.repository.ReservationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ReservationsTest {
    @Autowired
    private ReservationRepository reservationRepository;




    @Test
    public void testAddOneReservation(){
        Date date=new Date();
        Reservation r =new Reservation(1,7,new java.sql.Date(date.getTime()));
        reservationRepository.save(r);
        List<Reservation> reservationListl=new ArrayList<>();
        Iterable<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(reservationListl::add);
        assertEquals(7,reservationListl.get(reservationListl.size()-1).getGuestId());
        assertEquals(1,reservationListl.get(reservationListl.size()-1).getRoomId());
    }

    @Test
    public void testAddTwoReservations(){
        Date date=new Date();
        Reservation r =new Reservation(1,7,new java.sql.Date(date.getTime()));
        reservationRepository.save(r);
        Reservation r2 =new Reservation(3,4,new java.sql.Date(date.getTime()));
        reservationRepository.save(r2);
        List<Reservation> reservationListl=new ArrayList<>();
        Iterable<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(reservationListl::add);
        assertEquals(7,reservationListl.get(reservationListl.size()-2).getGuestId());
        assertEquals(1,reservationListl.get(reservationListl.size()-2).getRoomId());
        assertEquals(4,reservationListl.get(reservationListl.size()-1).getGuestId());
        assertEquals(3,reservationListl.get(reservationListl.size()-1).getRoomId());
    }

    @Test
    public void testAddOneReservationWhenDBisEmpty(){
        reservationRepository.deleteAll();
        Date date=new Date();
        Reservation r =new Reservation(1,7,new java.sql.Date(date.getTime()));
        reservationRepository.save(r);
        List<Reservation> reservationListl=new ArrayList<>();
        Iterable<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(reservationListl::add);
        assertEquals(7,reservationListl.get(reservationListl.size()-1).getGuestId());
        assertEquals(1,reservationListl.get(reservationListl.size()-1).getRoomId());
    }

    @Test
    public void testAddTwoReservationsWhenDBisEmpty(){
        reservationRepository.deleteAll();
        Date date=new Date();
        Reservation r =new Reservation(1,7,new java.sql.Date(date.getTime()));
        reservationRepository.save(r);
        Reservation r2 =new Reservation(3,4,new java.sql.Date(date.getTime()));
        reservationRepository.save(r2);
        List<Reservation> reservationListl=new ArrayList<>();
        Iterable<Reservation> reservations = reservationRepository.findAll();
        reservations.forEach(reservationListl::add);
        assertEquals(7,reservationListl.get(reservationListl.size()-2).getGuestId());
        assertEquals(1,reservationListl.get(reservationListl.size()-2).getRoomId());
        assertEquals(4,reservationListl.get(reservationListl.size()-1).getGuestId());
        assertEquals(3,reservationListl.get(reservationListl.size()-1).getRoomId());
    }

}
