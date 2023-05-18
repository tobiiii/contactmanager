package com.ps.contactmanager.exceptions.exceptionResolver;

import com.ps.contactmanager.exceptions.ContactmanagerException;
import com.ps.contactmanager.exceptions.ValidationException;
import com.ps.contactmanager.utils.JsonResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@CommonsLog
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public JsonResponse handleValidationExceptionException(ContactmanagerException e, HttpServletRequest request) {
        var jsonResponse = new JsonResponse();
        jsonResponse.setStatus(JsonResponse.STATUS.FAILED);
        jsonResponse.setErrorCode(e.getErrorCode());
        jsonResponse.setErrorMsg(e.getMessage());
        return jsonResponse;
    }
}