package ru.vadim.hostel.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import org.springframework.context.annotation.Scope;
import ru.vadim.hostel.entity.Apartment;
import ru.vadim.hostel.repository.ApartmentRepository;

@Scope("prototype")
public class ApartmentActor extends UntypedActor {

    private ApartmentRepository apartmentRepository;

    public ApartmentActor(ApartmentRepository apartmentRepository) {
        this.apartmentRepository = apartmentRepository;
    }

    public static Props props(final ApartmentRepository apartmentRepository) {
        return Props.create(new Creator<ApartmentActor>() {
            private static final long SERIAL_VERSION_ID = 1L;
            @Override
            public ApartmentActor create() throws Exception {
                return new ApartmentActor(apartmentRepository);
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Apartment) {
            Apartment apartment = (Apartment) message;
            apartmentRepository.save(apartment);
        } else {
            unhandled(message);
        }
    }
}
