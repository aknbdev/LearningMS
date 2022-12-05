package uz.aknb.app.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

    private Status status;
    private T payload;
    private List<ResponseError> errors;
    private String message;

    public static <T> Response<T> ok() {
        return ok(null);
    }

    public static <T> Response<T> ok(T body) {
        Response<T> response = new Response<>();
        response.setStatus(Status.OK);
        response.setPayload(body);
        return response;
    }

    public static <E> Response<E> errorResponse(Exception ex, Status status) {
        return new Response<E>()
                .setStatus(status)
                .setErrors(List.of(ResponseError.builder()
                        .fieldName(ex.getLocalizedMessage())
                        .errorMessage(ex.getMessage())
                        .build()))
                .setMessage(ex.getMessage());
    }

    public enum Status {
        OK,
        BAD_REQUEST,
        UNAUTHORIZED,
        NOT_VALID,
        EXCEPTION,
        WRONG_CREDENTIALS,
        ACCESS_DENIED,
        NOT_FOUND,
        DUPLICATE_ENTITY
    }
}

