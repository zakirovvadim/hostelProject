package ru.vadim.hostel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.service.ApartmentService;
import ru.vadim.hostel.service.GuestService;

import java.util.List;

@RestController
@RequestMapping(value = "api/apartments")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService service;
    private final GuestService guestService;

    // Добавление апартамента
    @PostMapping(value = "")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ApartmentDto> saveApartment(@RequestBody ApartmentDto dto) {
        return new ResponseEntity<>(service.save(dto), HttpStatus.OK);
    }

    // Удаление апартамента
    @DeleteMapping(value = "/{number}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteApartmentWithNumber(@PathVariable(name = "number") Long number) {
        service.delete(number);
        return ResponseEntity.ok().build();
    }

    // Получение гостей апартамента
    @GetMapping(value = "/guests")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<List<GuestDto>> getGuestOfApartment(@RequestParam(name = "number") Long number) {
        return new ResponseEntity<>(guestService.getGuestsFromApart(number), HttpStatus.OK);
    }

    // Получение количества помещений (в апартаменте)
    @GetMapping(value = "/rooms")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<Integer> getCountOfRooms(@RequestParam(name = "number") Long number) {
        return new ResponseEntity<>(service.getApartmentByNumber(number).getCountOfRooms(), HttpStatus.OK);
    }

    // Назначение категории апартаменту
    @PutMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    public ResponseEntity<ApartmentDto> appointCategory(@RequestParam(name = "idOfCategory") Long id,
                                             @RequestParam(name = "numberOfApartment") Long number) {
        return new ResponseEntity<>(service.appointCategoryToApartment(number, id), HttpStatus.OK);
    }
}
