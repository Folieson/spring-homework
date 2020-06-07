package com.folies.exceptions;

//Исключение выбрасывается при вызове сервиса с некорректными параметрами
public class EntityIllegalArgumentException extends BaseException {
    public EntityIllegalArgumentException(String message) {
        super(message);
    }
}
