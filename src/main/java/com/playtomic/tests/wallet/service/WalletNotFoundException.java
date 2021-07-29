package com.playtomic.tests.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the exception when a wallet doesn't exist.
 *
 */
public class WalletNotFoundException extends RuntimeException {
    
    private final Logger log = LoggerFactory.getLogger(WalletNotFoundException.class);

    WalletNotFoundException(Long id) {   
        super("Could not find wallet " + id);
        log.warn("Could not find wallet " +id);
    }
}
