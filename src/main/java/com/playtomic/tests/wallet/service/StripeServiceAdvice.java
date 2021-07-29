package com.playtomic.tests.wallet.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles the exception when trying to pay with less than 10€.
 *
 */
@ControllerAdvice
public class StripeServiceAdvice {
    @ResponseBody
    @ExceptionHandler(StripeServiceException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    String stripeServiceHandler(StripeServiceException ex) {
        return ex.getMessage();
    }
}
