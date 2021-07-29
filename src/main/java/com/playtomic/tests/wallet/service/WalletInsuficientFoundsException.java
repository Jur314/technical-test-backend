package com.playtomic.tests.wallet.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handles the exception when a wallet has insuficient founds.
 *
 */
public class WalletInsuficientFoundsException extends RuntimeException{
    private final Logger log = LoggerFactory.getLogger(WalletInsuficientFoundsException.class);

    WalletInsuficientFoundsException(Long id) {   
        super("Operation Cancelled, Insufiecient founds for wallet " + id);
        log.warn("WalletService:::updateBalance() Insuficient founds for wallet " +id);
    }
}
