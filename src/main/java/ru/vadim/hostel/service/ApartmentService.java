package ru.vadim.hostel.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.routing.FromConfig;
import com.typesafe.config.ConfigFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.vadim.hostel.actors.ApartmentActor;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.entity.dto.ApartmentDto;
import ru.vadim.hostel.entity.dto.CategoryDto;
import ru.vadim.hostel.exception.NoEntityException;
import ru.vadim.hostel.mapper.ApartmentMapper;
import ru.vadim.hostel.mapper.CategoryMapper;
import ru.vadim.hostel.repository.ApartmentRepository;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"apartment"})
public class ApartmentService {

    private final ApartmentRepository repository;
    private final ApartmentMapper mapper;
    private final CategoryMapper categoryMapper;
    private final CategoryService categoryService;

    @CacheEvict(value = "apartment", allEntries = true)
    public ApartmentDto save(ApartmentDto dto) {
        if (dto.getCategory() != null) {
            CategoryDto foundCategory = categoryService.findByName(dto.getCategory().getName());
            if (foundCategory.getName() != null) {
                dto.setCategory(foundCategory);
                Apartment apartment = mapper.map(dto);
                giveToActor(apartment);
                return mapper.map(apartment);
            } else {
                Apartment apartment = mapper.map(dto);
                giveToActor(apartment);
                return mapper.map(apartment);
            }
        } else {
            Apartment apartment = mapper.map(dto);
            giveToActor(apartment);
            return mapper.map(apartment);
        }
    }

    @CacheEvict(value = "apartment", allEntries = true)
    public void delete(Long number) {
        Apartment apartment = repository.findApartmentByNumber(number).orElseThrow(() -> new NoEntityException(number));
        repository.deleteById(apartment.getId());
    }

    public Integer getCountOfRooms(Long number) {
        return getApartmentByNumber(number).getCountOfRooms();
    }

    @Cacheable(value = "apartment")
    public Apartment getApartmentByNumber(Long number) {
        return repository.findApartmentByNumber(number).orElseThrow(() -> new NoEntityException(number));
    }

    @CacheEvict(value = "apartment", allEntries = true)
    public ApartmentDto appointCategoryToApartment(Long number, Long categoryId) {
        Apartment apartment = getApartmentByNumber(number);
        Category category = categoryService.getCategoryById(categoryId);
        apartment.setCategory(category);
        return mapper.map(repository.save(apartment));
    }

    public void giveToActor(Apartment apartment) {
        final ActorSystem system = ActorSystem.create("TaskSystem", ConfigFactory.load().getConfig("TaskSystem"));
        ActorRef router = system.actorOf(ApartmentActor.props(repository).withRouter(new FromConfig()), "TaskRouter");
        router.tell(apartment, null);
    }
}