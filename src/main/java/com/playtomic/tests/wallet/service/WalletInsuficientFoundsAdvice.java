package com.playtomic.tests.wallet.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handles the exception when a wallet has insuficient founds.
 *
 */
@ControllerAdvice
public class WalletInsuficientFoundsAdvice {
    @ResponseBody
    @ExceptionHandler(WalletInsuficientFoundsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String walletInsuficientFoundsHandler(WalletInsuficientFoundsException ex) {
        return ex.getMessage();
    }
}
