package ru.vadim.hostel.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.service.GuestService;

import java.util.List;

@RestController
@RequestMapping(value = "api/guests")
@RequiredArgsConstructor
@Api(tags = "Guest")
public class GuestController {
    private final GuestService service;

    // Добавление гостя
    @PostMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(description = "Create guest", responses = {@ApiResponse(responseCode = "200", description = "Guest was created")})
    public ResponseEntity<GuestDto> saveGuest(@RequestBody GuestDto guestDto) {
        return new ResponseEntity<>(service.save(guestDto), HttpStatus.OK);
    }

    // Удаление гостя
    @DeleteMapping(value = "/{passportNumber}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(description = "Delete guest", responses = {@ApiResponse(responseCode = "200", description = "Guest was deleted")})
    public ResponseEntity<Void> deleteGuest(@PathVariable(name = "passportNumber") String passportNumber) {
        service.delete(passportNumber);
        return ResponseEntity.ok().build();
    }

    //Получение списка гостей
    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(description = "Get list of all guests", responses = {@ApiResponse(responseCode = "200", description = "List of all guests received")})
    public List<GuestDto> getGuests() {
        return service.getGuests();
    }

    // Редактирование гостя
    @PutMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(description = "Update information about guest", responses = {@ApiResponse(responseCode = "200", description = "Guest was updated")})
    public ResponseEntity<GuestDto> updateGuest(@RequestBody GuestDto guestDto) {
        return new ResponseEntity<>(service.updateGuest(guestDto), HttpStatus.OK);
    }

    //Назначение гостю апартаментов
    @PutMapping(value = "/appointment")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(description = "Appoint guest to apartment", responses = {@ApiResponse(responseCode = "200", description = "Apartment was appointed")})
    public ResponseEntity<GuestDto> appointApartment(@RequestParam(name = "passportNumber") String passportNumber,
                                                  @RequestParam(name = "numberOfApartment") Long number) {
        return new ResponseEntity<GuestDto>(service.appointGuestToApartment(passportNumber, number), HttpStatus.OK);
    }
}