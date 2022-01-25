package ru.vadim.hostel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> saveGuest(@RequestBody GuestDto guestDto) {
        service.save(guestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // Удаление гостя
    @DeleteMapping(value = "/{passportNumber}")
    public ResponseEntity<?> deleteGuest(@PathVariable(name = "passportNumber") Integer passportNumber) {
        final boolean deleted = service.delete(passportNumber);
        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //Получение списка гостей
    @GetMapping(value = "")
    public List<GuestDto> getGuests() {
        return service.getGuests();
    }

    // Редактирование гостя
    @PutMapping(value = "")
    public ResponseEntity<?> updateGuest(GuestDto guestDto) {
        GuestDto updated = service.updateGuest(guestDto);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
