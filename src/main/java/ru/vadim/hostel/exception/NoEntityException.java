package ru.vadim.hostel.exception;

public class NoEntityException extends RuntimeException {
    public NoEntityException(long number) {
        super("The attribute with number: " + number + " isn't exist");
    }
}
