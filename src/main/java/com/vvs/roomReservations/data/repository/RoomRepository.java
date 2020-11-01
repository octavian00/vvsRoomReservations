package com.vvs.roomReservations.data.repository;

import com.vvs.roomReservations.data.entity.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Repository
public interface RoomRepository extends CrudRepository<Room,Long> {
    @RestController
    @RequestMapping("/rooms")
    class RoomController{
        @Autowired
        private RoomRepository roomRepository;

        @GetMapping
        public Iterable<Room> getRooms(){
            return this.roomRepository.findAll();
        }
    }
}
