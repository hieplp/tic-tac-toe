package vn.hieplp.todo.common.handler;

public interface IValidationHandler {
    IValidationHandler checkNullAll(Object object);

    IValidationHandler checkCanNullWithAnnotation(Object object);

    IValidationHandler checkNotNullWithAnnotation(Object object);

    IValidationHandler checkValidEmail(Object object);

    IValidationHandler checkLength(Object object);

    IValidationHandler isValidEmail(String email);
}
