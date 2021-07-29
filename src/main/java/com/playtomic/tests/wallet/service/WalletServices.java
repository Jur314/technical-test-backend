package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.model.WalletModel;
import com.playtomic.tests.wallet.repository.WalletRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Handles the operations with a wallet.
 *
 */
@Service
public class WalletServices {

    @Autowired
    WalletRepository walletRepository;

    @Autowired
    StripeService stripeService;

    /**
     * Return a wallet by his id and, in case is not founded
     * throws an exception
     *
     * @param id The wallet id
     * @return wallet The wallet with all the information
     *
     * @throws WalletNotFoundException
     */
    public WalletModel walletInfo(long id) {

        return walletRepository.findById(id)
        .orElseThrow(() -> new WalletNotFoundException(id));
    }

    /**
     * Charge credits in a wallet by his id. Also, conects with
     * stripeService to pay with a credit card. The amount of credits
     * that are going to charge in a wallet are the same as the amount 
     * of money pay with a credit card.
     *
     * @param wallet The wallet with the id and the currentBalance
     * @return wallet The wallet with all the information
     *
     * @throws WalletNotFoundException
     */
    public WalletModel pay(WalletModel wallet) {
        WalletModel walletToUpdate = walletRepository.findById(wallet.getId())
            .orElseThrow(() -> new WalletNotFoundException(wallet.getId()));

        walletToUpdate.setCurrentBalance(walletToUpdate.getCurrentBalance().add(wallet.getCurrentBalance()));

        stripeService.charge("4242 4242 4242 4242", walletToUpdate.getCurrentBalance());
        walletRepository.save(walletToUpdate);

        return walletToUpdate;
    }

    /**
     * Update the current balance of a wallet.
     *
     * @param wallet The wallet with the id and the currentBalance
     * @return wallet The wallet with all the information
     *
     * @throws WalletNotFoundException
     * @throws WalletInsuficientFoundsException
     */
    public WalletModel updateBalance(WalletModel wallet) {
        WalletModel walletToUpdate = walletRepository.findById(wallet.getId())
            .orElseThrow(() -> new WalletNotFoundException(wallet.getId()));

        if(wallet.getCurrentBalance().compareTo(walletToUpdate.getCurrentBalance()) <= 0) {
            walletToUpdate.setCurrentBalance(walletToUpdate.getCurrentBalance().subtract(wallet.getCurrentBalance()));
            
        } else {
            throw new WalletInsuficientFoundsException(wallet.getId());
        }

        return walletRepository.save(walletToUpdate);
    }
}
