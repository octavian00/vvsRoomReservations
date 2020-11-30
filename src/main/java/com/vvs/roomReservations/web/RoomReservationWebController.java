package com.vvs.roomReservations.web;

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
