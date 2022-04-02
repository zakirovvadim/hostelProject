package ru.vadim.hostel.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;

import java.util.Arrays;

@Slf4j
@Service
public class BrokerErrorHandler implements ErrorHandler {
    @Override
    public void handleError(Throwable t) {
        log.trace("Error in listener", t);
        System.out.println("START - " + Arrays.toString(t.getStackTrace()) + " - STOP");
    }
}
