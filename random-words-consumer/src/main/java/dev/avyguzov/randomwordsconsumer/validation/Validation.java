package dev.avyguzov.randomwordsconsumer.validation;

public interface Validation<T> {
    void test(T obj) throws ValidationError;
}
