package uz.aknb.app.exception;


public interface ExceptionHandler {

    /**
     * Returns a new RuntimeException
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    default RuntimeException exception(String entityType, ExceptionType exceptionType, String... args) {
        throw GeneralException.throwException(entityType, exceptionType, args);
    }

    default RuntimeException exception(ExceptionType exceptionType, String entityType, String... args) {
        throw GeneralException.throwExceptionWithTemplate(exceptionType, entityType, args);
    }
}
