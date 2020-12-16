package com.vvs.roomReservations.web;

import com.vvs.roomReservations.model.dto.RoomReservation;
import com.vvs.roomReservations.model.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomReservationWebControllerTest {

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private MockMvc mockMvc;
    @Test
    @DisplayName("Get Home page -> status 200")
    public void whenGetHomePage_then200() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(view().name("forward:index.html"));
    }

    @Test
    @DisplayName("GetAddReservations -> status 200")
    public void whenGetAddReservation_then200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/addReservation")).andExpect(status().isOk())
                .andExpect(view().name("addReservation"));
    }

    @Test
    @DisplayName("GetReservations -> status 200")
    public void whenGetReservations_then200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/reservations")).andExpect(status().isOk())
                .andExpect(view().name("reservations"));
    }

    @Test
    @DisplayName("WrongPage -> status 404")
    public void whenGetWrongPage_then404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/wrong")).andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("WrongPage -> status 302")
    public void whenPostAdd_then302() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/addReservation")).andExpect(status().isFound())
        .andExpect(view().name("redirect:/"));

    }

    @Test
    public void getReservations() throws Exception{
        String dateString = "2020-01-01";
        Date date = DateUtils.createDateFromDateString(dateString);
        List<RoomReservation> roomReservations = new ArrayList<>();
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setLastName("zuzu");
        roomReservation.setFirstName("mumu");
        roomReservation.setDate(date);
        roomReservation.setGuestId(new Long(1));
        roomReservation.setRoomId(new Long(100));
        roomReservation.setRoomName("Junit Room");
        roomReservation.setRoomNumber("J1");
        roomReservations.add(roomReservation);
        given(reservationService.getRoomReservationForDate(date)).willReturn(roomReservations);
        this.mockMvc.perform(get("/reservations?date=2020-01-01"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("zuzu, mumu")));
    }

    @Test
    public void getReservationsWithWrongDate() throws Exception{
        String dateString = "A";
        Date date = DateUtils.createDateFromDateString(dateString);
        List<RoomReservation> roomReservations = new ArrayList<>();
        RoomReservation roomReservation = new RoomReservation();
        roomReservation.setLastName("zuzu");
        roomReservation.setFirstName("mumu");
        roomReservation.setDate(date);
        roomReservation.setGuestId(new Long(1));
        roomReservation.setRoomId(new Long(100));
        roomReservation.setRoomName("Junit Room");
        roomReservation.setRoomNumber("J1");
        roomReservations.add(roomReservation);
        given(reservationService.getRoomReservationForDate(date)).willReturn(roomReservations);
        this.mockMvc.perform(get("/reservations?A"))
                .andExpect(status().isOk());
    }
}
