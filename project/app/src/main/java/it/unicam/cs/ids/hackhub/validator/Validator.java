package it.unicam.cs.ids.hackhub.validator;

public interface Validator<T> {
    boolean validate(T entity);
}
