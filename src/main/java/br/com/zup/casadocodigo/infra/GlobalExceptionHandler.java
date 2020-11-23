package br.com.zup.casadocodigo.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String GENERIC_ERROR = "Ocorreu um erro inesperado no sistema. Tente mais tarde";

    public GlobalExceptionHandler (MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus (code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ApiErrorReturn> handleControllerValidation (MethodArgumentNotValidException exception) {

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();

        return fieldErrors.stream().map(fieldError -> {
            String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
            return new ApiErrorReturn(fieldError.getField(), message);
        }).collect(Collectors.toList());
    }

    @ResponseStatus (code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler (ConstraintViolationException.class)
    public List<ApiErrorReturn> handleInvalidState (ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        return constraintViolations
                .stream()
                .map(e -> new ApiErrorReturn(e.getPropertyPath().toString(), e.getMessage()))
                .collect(Collectors.toList());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex) {
        logger.error(ex.getMessage(), ex);

        Map<String, String> ERROR = Map.of("message", GENERIC_ERROR);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(ERROR);
    }

}
