package com.folies.exceptions;

import org.springframework.util.Assert;

//Исключение выбрасывается при попытке удаление сущности, на которую есть ссылки у других сущностей
public class EntityHasDetailsException extends BaseException {
    public EntityHasDetailsException(String message) {
        super(message);
    }

    public EntityHasDetailsException(String type, Object id) {
        this(formatMessage(type, id));
    }

    private static String formatMessage(String type, Object id) {
        Assert.hasText(type, "Тип не может быть пустым");
        Assert.notNull(id, "Идентификатор объекта не может быть null");
        Assert.hasText(id.toString(), "Идентификатор не может быть пустым");
        return String.format("%s ссылается на удаляемый объеккт с идентификатором %s", type, id);
    }
}
