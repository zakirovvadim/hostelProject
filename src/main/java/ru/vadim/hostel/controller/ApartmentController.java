package ru.vadim.hostel.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Api(tags = "Apartments")
public class ApartmentController {
    private final ApartmentService service;
    private final GuestService guestService;

    // Добавление апартамента
    @PostMapping(value = "")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(description = "Create apartment", responses = {@ApiResponse(responseCode = "200", description = "Apartment was added")})
    public ResponseEntity<ApartmentDto> saveApartment(@RequestBody ApartmentDto apartment) {
        return new ResponseEntity<>(service.save(apartment), HttpStatus.OK);
    }

    // Удаление апартамента
    @DeleteMapping(value = "/{number}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(description = "Delete apartment by number", responses = {@ApiResponse(responseCode = "200", description = "Apartment was deleted")})
    public ResponseEntity<Void> deleteApartmentByNumber(@PathVariable(name = "number") Long number) {
        service.delete(number);
        return ResponseEntity.ok().build();
    }

    // Получение гостей апартамента
    @GetMapping(value = "/guests")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(description = "Get guests whose live in apartment", responses = {@ApiResponse(responseCode = "200", description = "List of guests received")})
    public ResponseEntity<List<GuestDto>> getGuestOfApartment(@RequestParam(name = "number") Long number) {
        return new ResponseEntity<>(guestService.getGuestsFromApart(number), HttpStatus.OK);
    }

    // Получение количества помещений (в апартаменте)
    @GetMapping(value = "/rooms")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(description = "Get count of rooms in apartment", responses = {@ApiResponse(responseCode = "200", description = "Count was received")})
    public ResponseEntity<Integer> getCountOfRooms(@RequestParam(name = "number") Long number) {
        return new ResponseEntity<>(service.getApartmentByNumber(number).getCountOfRooms(), HttpStatus.OK);
    }

    // Назначение категории апартаменту
    @PutMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @Operation(description = "Appoint category to apartment", responses = {@ApiResponse(responseCode = "200", description = "Category was appointed")})
    public ResponseEntity<ApartmentDto> appointCategory(@RequestParam(name = "idOfCategory") Long id,
                                             @RequestParam(name = "numberOfApartment") Long number) {
        return new ResponseEntity<>(service.appointCategoryToApartment(number, id), HttpStatus.OK);
    }
}
