package ru.vadim.hostel.exception;


import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NoEntityException extends RuntimeException {
    public NoEntityException(long number) {
        super("The attribute with number: " + number + " isn't exist");
        log.error("The attribute with number: " + number + " isn't exist");
    }
    public NoEntityException(String number) {
        super("The attribute with number: " + number + " isn't exist");
        log.error("The attribute with number: " + number + " isn't exist");
    }
}
