package com.playtomic.tests.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the exception when trying to pay with less than 10€.
 *
 */
public class StripeServiceException extends RuntimeException {
    private final Logger log = LoggerFactory.getLogger(StripeServiceException.class);

    StripeServiceException() {   
        super("Operation Cancelled, trying to charge less than 10€ ");
        log.warn("StripeService:::charge() with an issue, trying to charge less than 10€ ");
    }
}
