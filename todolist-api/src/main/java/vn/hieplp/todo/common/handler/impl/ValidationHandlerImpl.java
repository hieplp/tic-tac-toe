package vn.hieplp.todo.common.handler.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vn.hieplp.todo.common.annotations.*;
import vn.hieplp.todo.common.exceptions.CommonException;
import vn.hieplp.todo.common.handler.IValidationHandler;
import vn.hieplp.todo.common.utils.States;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;

/**
 * Copyright by HiepLP.
 * Creator: Ly Phuoc Hiep
 * Date: 09/05/2022
 * Time: 13:56
 */
public class ValidationHandlerImpl implements IValidationHandler {
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    public IValidationHandler checkNullAll(Object object) {
        LOGGER.info("Check null all");
        try {
            this.checkObjectIsNull(object);
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (States.isNull(field.get(object))) {
                    throw new CommonException.ValidationException("Property " + field.getName() + " is null");
                }
            }
            return this;
        } catch (IllegalAccessException e) {
            LOGGER.error("Error with accessible object cause by {}", e.getMessage());
            throw new CommonException.ValidationException("Error when check object length with annotation");
        }
    }

    @Override
    public IValidationHandler checkCanNullWithAnnotation(Object object) {
        LOGGER.info("Check can null with annotation");
        try {
            this.checkObjectIsNull(object);
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                if (field.isAnnotationPresent(CanNullOrEmpty.class) || field.isAnnotationPresent(CanNull.class)) {
                    continue;
                }

                if (field.isAnnotationPresent(CanEmpty.class) && States.isNull(field.get(object))) {
                    throw new CommonException.ValidationException("Property " + field.getName() + " is null");
                }

                if (States.isNull(field.get(object)) || States.isNullOrEmpty(String.valueOf(field.get(object)))) {
                    throw new CommonException.ValidationException("Property " + field.getName() + " is null or empty");
                }
            }
            return this;
        } catch (IllegalAccessException e) {
            LOGGER.error("Error with accessible object cause by {}", e.getMessage());
            throw new CommonException.ValidationException("Error when check object length with annotation");
        }
    }

    @Override
    public IValidationHandler checkNotNullWithAnnotation(Object object) {
        LOGGER.info("Check not null with annotation");
        try {
            this.checkObjectIsNull(object);
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(NotNullOrEmpty.class) || field.isAnnotationPresent(NotNull.class)) {
                    if (States.isNull(field.get(object))) {
                        throw new CommonException.ValidationException("Property " + field.getName() + " is null");
                    }
                }

                if (field.isAnnotationPresent(NotNullOrEmpty.class)) {
                    // Check if string is null or empty
                    // Check if list is null or empty
                    if ((field.get(object) instanceof String && States.isNullOrEmpty((String) field.get(object)))
                            || (field.get(object) instanceof List && States.isNullOrEmpty((Collection<?>) field.get(object)))) {
                        throw new CommonException.ValidationException("Property " + field.getName() + " is null or empty");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            LOGGER.error("Error with accessible object cause by {}", e.getMessage());
            throw new CommonException.ValidationException("Error when check not null with annotation");
        }

        return this;
    }

    @Override
    public IValidationHandler checkValidEmail(Object object) {
        LOGGER.info("Check valid email with annotation");
        final String regex = "^[\\w-_.+]*[\\w-_.]@([\\w]+\\.)+[\\w]+[\\w]$";
        try {
            this.checkObjectIsNull(object);
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Email.class)) {
                    String email = (String) field.get(object);
                    if (!email.matches(regex)) {
                        throw new CommonException.ValidationException("Invalid email");
                    }
                }
            }

            return this;
        } catch (IllegalAccessException e) {
            LOGGER.error("Error with accessible object cause by {}", e.getMessage());
            throw new CommonException.ValidationException("Error when check object length with annotation");
        }
    }

    @Override
    public IValidationHandler checkLength(Object object) {
        LOGGER.info("Check length with annotation");
        try {
            this.checkObjectIsNull(object);
            for (Field field : object.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                String str = (String) field.get(object);
                if (field.isAnnotationPresent(MinLength.class)) {
                    MinLength minLength = field.getAnnotation(MinLength.class);
                    if (str.length() < minLength.length()) {
                        throw new CommonException.ValidationException("Property " + field.getName() + " length < min length");
                    }
                }
                if (field.isAnnotationPresent(MaxLength.class)) {
                    MaxLength maxLength = field.getAnnotation(MaxLength.class);
                    if (str.length() > maxLength.length()) {
                        throw new CommonException.ValidationException("Property " + field.getName() + " length > max length");
                    }
                }
            }

            return this;
        } catch (IllegalAccessException e) {
            LOGGER.error("Error with accessible object cause by {}", e.getMessage());
            throw new CommonException.ValidationException("Error when check object length with annotation");
        }
    }

    @Override
    public IValidationHandler isValidEmail(String email) {
        LOGGER.info("Check mail {} is valid", email);
        return this;
    }

    private void checkObjectIsNull(Object object) {
        if (States.isNull(object)) {
            throw new CommonException.ValidationException("Object is null");
        }
    }
}
