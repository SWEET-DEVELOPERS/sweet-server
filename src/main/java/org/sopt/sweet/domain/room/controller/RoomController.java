package org.sopt.sweet.domain.room.controller;

import lombok.RequiredArgsConstructor;
import org.sopt.sweet.domain.room.service.RoomService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/room")
@RestController
public class RoomController implements RoomApi {

    private final RoomService roomService;

}
