package ru.vadim.hostel.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.repository.CategoryRepository;

public class CategoryActor extends UntypedActor {

    private CategoryRepository categoryRepository;

    public CategoryActor(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static Props props(final CategoryRepository categoryRepository) {
        return Props.create(new Creator<CategoryActor>() {
            private static final long SERIAL_VERSION_ID = 1L;

            @Override
            public CategoryActor create() throws Exception {
                return new CategoryActor(categoryRepository);
            }
        });
    }

    @Override
    public void onReceive(Object message)  {
        try {
            if (message instanceof Category category) {
                categoryRepository.save(category);
            } else {
                unhandled(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
