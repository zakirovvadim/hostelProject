package ru.vadim.hostel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.vadim.hostel.consumer.events.DeleteEvent.DeleteEvent;
import ru.vadim.hostel.consumer.events.Events;
import ru.vadim.hostel.consumer.events.SaveUtil.SaveEvent;
import ru.vadim.hostel.consumer.utils.Sender;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "api/category")
@RequiredArgsConstructor
@Api(tags = "Category")
public class CategoryController {
    private final CategoryService service;
    private final ObjectMapper mapper;
    private final JmsTemplate jmsTemplate;


    @PostMapping(value = "")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(description = "Create category", responses = {@ApiResponse(responseCode = "200", description = "Category was created")})
    public ResponseEntity<CategoryDto> saveCategory(@RequestBody CategoryDto categoryDto) {
        SaveEvent saveEvent = new SaveEvent();
        saveEvent.setEvents(Events.CATEGORY);
        saveEvent.setObject(categoryDto);
        Sender.saveThrowBroker(mapper, jmsTemplate, saveEvent);
        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(description = "Delete category by id", responses = {@ApiResponse(responseCode = "200", description = "Category was deleted")})
    public ResponseEntity<Void> deleteCategory(@PathVariable(name = "id") Long id) {
        DeleteEvent deleteEvent = new DeleteEvent();
        deleteEvent.setEvents(Events.CATEGORY);
        deleteEvent.setNumber(id.toString());
        Sender.deleteThrowBroker(mapper, jmsTemplate, deleteEvent);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
    @Operation(description = "Get list of category", responses = {@ApiResponse(responseCode = "200", description = "List of category received")})
    public List<CategoryDto> getAllCategories() {
        return service.getAllCategories();
    }
}