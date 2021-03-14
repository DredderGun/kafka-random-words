package dev.avyguzov.randomwordsconsumer.validation;

public class ValidationError extends Exception {
    public ValidationError(String text) {
        super(text);
    }
}
