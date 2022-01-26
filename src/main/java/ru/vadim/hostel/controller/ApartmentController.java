package ru.vadim.hostel.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.entity.dto.GuestDto;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.mapper.GuestMapper;
import ru.vadim.hostel.service.ApartmentService;

import java.util.List;

@RestController
@RequestMapping(value = "api/apartments")
@RequiredArgsConstructor
public class ApartmentController {
    private final ApartmentService service;
    private final GuestMapper guestMapper;
    private final CategoryMapper categoryMapper;

    // Добавление апартамента
    @PostMapping(value = "")
    public ResponseEntity<ApartmentDto> saveApartment(@RequestBody ApartmentDto dto) {
        ApartmentDto savedApartment = service.save(dto);
        return new ResponseEntity<>(savedApartment, HttpStatus.OK);
    }

    // Удаление апартамента
    @DeleteMapping(value = "/{number}")
    public ResponseEntity<?> deleteApartmentWithNumber(@PathVariable(name = "number") Long number) {
        final boolean deleted = service.delete(number);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Получение гостей апартамента
    @GetMapping(value = "/guests")
    public List<GuestDto> getGuestOfApartment(@PathVariable(name = "number") Long number) {
        ApartmentDto dto = service.getApartmentByNumber(number);
        List<GuestDto> guestDtoList = dto.getGuests();
        return guestDtoList;
    }

    // Получение количества помещений (в апартаменте)
    @GetMapping(value = "/rooms")
    public ResponseEntity<?> getCountOfRooms(@PathVariable(name = "number") Long number) {
        ApartmentDto dto = service.getApartmentByNumber(number);
        Integer countOfRooms = dto.getCountOfRooms();
        return countOfRooms != null
                ? new ResponseEntity<>(countOfRooms, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Назначение категории апартаменту
    @PutMapping(value = "")
    public ResponseEntity<?> appointCategory(@RequestBody CategoryDto categoryDto,
                                             @PathVariable(name = "numberOfApartment") Long number) {
        ApartmentDto dto = service.getApartmentByNumber(number);
        dto.setCategory(categoryDto);
        service.save(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
