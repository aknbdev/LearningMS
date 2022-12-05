package uz.aknb.app.exception;

import org.apache.commons.lang3.StringUtils;

public enum ExceptionType {
    ENTITY_NOT_FOUND("not.found"),
    UNAUTHORIZED("unauthorized"),
    DUPLICATE_ENTITY("duplicate"),
    EXCEPTION("exception"),
    EXPIRED("expired");

    String value;

    ExceptionType(String value) {
        this.value = value;
    }

    public String getMessageTemplate(String entityType) {
        return StringUtils.isNotBlank(entityType) ? StringUtils.joinWith(".", entityType, value) : value;
    }
}
