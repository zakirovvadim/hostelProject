package ru.vadim.hostel.actors;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.japi.Creator;
import org.springframework.context.annotation.Scope;
import ru.vadim.hostel.entity.Guest;
import ru.vadim.hostel.repository.GuestRepository;

@Scope("prototype")
public class GuestActor extends UntypedActor {
    private GuestRepository guestRepository;

    public GuestActor(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public static Props props(final GuestRepository guestRepository) {
        return Props.create(new Creator<GuestActor>() {
            private static final long SERIAL_VERSION_ID = 1L;
            @Override
            public GuestActor create() throws Exception {
                return new GuestActor(guestRepository);
            }
        });
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Guest) {
            Guest guest = (Guest) message;
            guestRepository.save(guest);
        }
    }
}
