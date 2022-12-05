package uz.aknb.app.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.aknb.app.response.Response;
import uz.aknb.app.response.ResponseError;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(
            GeneralException.EntityNotFoundException.class
    )
    public final ResponseEntity<Response<Object>> handleNotFountExceptions(Exception ex) {
        return new ResponseEntity<>(
                Response.errorResponse(ex, Response.Status.NOT_FOUND),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(
            GeneralException.DuplicateEntityException.class
    )
    public final ResponseEntity<Response<Object>> handleDuplicateEntityExceptions(Exception ex) {

        return new ResponseEntity<>(
                Response.errorResponse(ex, Response.Status.DUPLICATE_ENTITY),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler({
            GeneralException.SessionExpiredException.class,
            GeneralException.UnauthorizedException.class
    })
    public final ResponseEntity<Response<Object>> handleSessionExpiredException(Exception ex) {

        return new ResponseEntity<>(
                Response.errorResponse(ex, Response.Status.UNAUTHORIZED),
                HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Response<Object>> handleRuntimeException(Exception ex) {
        return new ResponseEntity<>(Response.errorResponse(ex, Response.Status.BAD_REQUEST), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ResponseError> errorList = ex
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> {
                    return ResponseError.builder()
                            .fieldName(fieldError.getField())
                            .errorMessage(fieldError.getDefaultMessage())
                            .build();
                })
                .collect(Collectors.toList());

        return handleExceptionInternal(ex, new Response<>().setStatus(Response.Status.NOT_VALID).setErrors(errorList),
                headers, HttpStatus.BAD_REQUEST, request);
    }
}
