package ru.vadim.hostel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vadim.hostel.entity.Guest;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.service.GuestService;

import java.util.List;

@RestController
@RequestMapping(value = "api/guests")
@RequiredArgsConstructor
public class GuestController {
    private final GuestService service;

    // Добавление гостя
    @PostMapping(value = "")
    public ResponseEntity<GuestDto> saveGuest(@RequestBody GuestDto guestDto) {
        return new ResponseEntity<>(service.save(guestDto), HttpStatus.OK);
    }

    // Удаление гостя
    @DeleteMapping(value = "/{passportNumber}")
    public ResponseEntity<Void> deleteGuest(@PathVariable(name = "passportNumber") Long passportNumber) {
        service.delete(passportNumber);
        return ResponseEntity.ok().build();
    }

    //Получение списка гостей
    @GetMapping(value = "")
    public List<GuestDto> getGuests() {
        return service.getGuests();
    }

    // Редактирование гостя
    @PutMapping(value = "")
    public ResponseEntity<GuestDto> updateGuest(@RequestBody GuestDto guestDto) {
        return new ResponseEntity<>(service.updateGuest(guestDto), HttpStatus.OK);
    }

    //Назначение гостю апартаментов
    @PutMapping(value = "/appoint")
    public ResponseEntity<Guest> appointApartment(@RequestParam(name = "passportNumber") Long passportNumber,
                                                  @RequestParam(name = "numberOfApartment") Long number) {
        return new ResponseEntity<>(service.appointGuestToApartment(passportNumber, number), HttpStatus.OK);
    }
}