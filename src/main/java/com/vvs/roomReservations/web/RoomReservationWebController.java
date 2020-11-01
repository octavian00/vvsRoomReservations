package com.vvs.roomReservations.web;

import com.vvs.roomReservations.data.entity.Guest;
import com.vvs.roomReservations.data.entity.Room;
import com.vvs.roomReservations.model.dto.GuestReservation;
import com.vvs.roomReservations.model.dto.RoomReservation;
import com.vvs.roomReservations.model.service.ReservationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@Controller

public class RoomReservationWebController {
    private final ReservationService reservationService;

    public RoomReservationWebController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @RequestMapping("/reservations")
    @GetMapping
    public String getReservations(@RequestParam(value = "date",required = false)String dateString, Model model){
        Date date=DateUtils.createDateFromDateString(dateString);
        List<RoomReservation> roomReservations = this.reservationService.getRoomReservationForDate(date);
        model.addAttribute("roomReservations",roomReservations);
        return "reservations";
    }

//    @RequestMapping("/createReservation")
//    @PostMapping
//    public String createReservation(Model model){
//        GuestReservation guestt = new GuestReservation();
//        List<GuestReservation> guestList = this.reservationService.getGuests();
//        model.addAttribute("guests",guestList);
//        model.addAttribute("zuzu",guestt);
//        System.out.println(guestt.getEmail());
//        return "createReservation";
//    }
    @RequestMapping("/addGuest")
    public String createGuest(Model model){
        GuestReservation guest = new GuestReservation();
        model.addAttribute("zuzu",guest);
        return "addGuest";
    }

    @RequestMapping(value = "/addGuestt", method = RequestMethod.POST)
    public String addGuest(@ModelAttribute("zuzu")GuestReservation guest){
        System.out.println(guest.getEmail());
        //reservationService.add(guest);
        return  "redirect:/";
    }
    @RequestMapping("/addRoom")
    public String addRoom(Model model){
        Room room =new Room();
        model.addAttribute("room",room);
        return "addRoom";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addRoom(@ModelAttribute("room")Room room){
        System.out.println(room.getRoomName());
        //reservationService.addRoom(room);
        return  "redirect:/";
    }

    @RequestMapping("/addReservation")
    public String addReservation(Model model){
        RoomReservation roomReservation = new RoomReservation();
        model.addAttribute("roomReservation",roomReservation);
        return "addReservation";
    }

    @RequestMapping(value = "/addReservation", method = RequestMethod.POST)
    public String addReservationToDatabase(@ModelAttribute("roomReservation")RoomReservation roomReservation){
        reservationService.addReservation(roomReservation);
        return  "redirect:/";
    }
}
