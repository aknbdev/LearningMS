package uz.aknb.app.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;
import java.util.Locale;

@Slf4j
@Component
public class GeneralException {

    private static MessageSource messageSource;

    public GeneralException(MessageSource messageSource) {
        GeneralException.messageSource = messageSource;
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    public static RuntimeException throwException(String messageTemplate, String... args) {
        return new RuntimeException(format(messageTemplate, args));
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public static RuntimeException throwException(String entityType, ExceptionType exceptionType, String... args) {
        String messageTemplate = exceptionType.getMessageTemplate(entityType);
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType and args
     *
     * @param entityType
     * @param exceptionType
     * @param args
     * @return
     */
    public static RuntimeException throwExceptionWithId(String entityType, ExceptionType exceptionType, String id, String... args) {
        String messageTemplate = exceptionType.getMessageTemplate(entityType).concat(".").concat(id);
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on EntityType, ExceptionType, messageTemplate and args
     *
     * @param exceptionType
     * @param messageTemplate
     * @param args
     * @return
     */
    public static RuntimeException throwExceptionWithTemplate(ExceptionType exceptionType, String messageTemplate, String... args) {
        return throwException(exceptionType, messageTemplate, args);
    }

    /**
     * Returns new RuntimeException based on template and args
     *
     * @param messageTemplate
     * @param args
     * @return
     */
    private static RuntimeException throwException(ExceptionType exceptionType, String messageTemplate, String... args) {
        if (ExceptionType.ENTITY_NOT_FOUND.equals(exceptionType)) {
            return new EntityNotFoundException(format(messageTemplate, args));
        } else if (ExceptionType.DUPLICATE_ENTITY.equals(exceptionType)) {
            return new DuplicateEntityException(format(messageTemplate, args));
        } else if (ExceptionType.EXPIRED.equals(exceptionType)) {
            return new SessionExpiredException(format(messageTemplate, args));
        } else if (ExceptionType.UNAUTHORIZED.equals(exceptionType)) {
            return new UnauthorizedException(format(messageTemplate, args));
        }
        return new RuntimeException(format(messageTemplate, args));
    }

    private static String format(String template, String... args) {
        String templateContent = null;
        try {
            templateContent = messageSource.getMessage(template, args, Locale.US);
        } catch (NoSuchMessageException e) {
            log.error("NoSuchMessageException: {}", e);
        }

        if (StringUtils.isNotBlank(templateContent)) {
            return templateContent;
        }
        return String.format(template, args);
    }

    public static class EntityNotFoundException extends RuntimeException {
        public EntityNotFoundException(String message) {
            super(message);
        }
    }

    public static class UnauthorizedException extends RuntimeException {
        public UnauthorizedException(String message) {
            super(message);
        }
    }

    public static class DuplicateEntityException extends RuntimeException {
        public DuplicateEntityException(String message) {
            super(message);
        }
    }

    public static class SessionExpiredException extends RuntimeException {
        public SessionExpiredException(String message) {
            super(message);
        }
    }

}
