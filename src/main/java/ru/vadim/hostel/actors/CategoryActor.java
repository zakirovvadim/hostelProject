package ru.vadim.hostel.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import org.springframework.context.annotation.Scope;
import ru.vadim.hostel.entity.Category;
import ru.vadim.hostel.repository.CategoryRepository;

@Scope("prototype")
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
    public void onReceive(Object message) throws Exception  {
            if (message instanceof Category) {
                Category category = (Category) message;
                System.out.println(category);
                categoryRepository.save(category);
            } else {
                unhandled(message);
            }
    }
}
