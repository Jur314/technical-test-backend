package com.playtomic.tests.wallet.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles the exception when a wallet doesn't exist.
 *
 */
@ControllerAdvice
public class WalletNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String walletNotFoundHandler(WalletNotFoundException ex) {
        return ex.getMessage();
    }
}
