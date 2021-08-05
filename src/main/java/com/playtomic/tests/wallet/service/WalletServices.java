package com.playtomic.tests.wallet.service;

import com.playtomic.tests.wallet.assemblers.WalletModelAssembler;
import com.playtomic.tests.wallet.entities.WalletEntity;
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

    @Autowired
    WalletModelAssembler walletModelAssembler;

    /**
     * Return a wallet by his id and, in case is not founded
     * throws an exception
     *
     * @param id The wallet id
     * @return WalletModel The wallet with all the information
     *
     * @throws WalletNotFoundException
     */
    public WalletModel walletInfo(long id) {

        return walletRepository.findById(id)
            .map(walletModelAssembler::toModel)
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

        WalletModel walletToUpdate = walletInfo(wallet.getId());

        stripeService.charge("4242 4242 4242 4242", wallet.getCurrentBalance());

        walletToUpdate.setCurrentBalance(walletToUpdate.getCurrentBalance().add(wallet.getCurrentBalance()));

        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setId(walletToUpdate.getId());
        walletEntity.setCurrentBalance(walletToUpdate.getCurrentBalance());

        walletRepository.save(walletEntity);

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
        WalletModel walletToUpdate = walletInfo(wallet.getId());

        if(wallet.getCurrentBalance().compareTo(walletToUpdate.getCurrentBalance()) <= 0) {
            walletToUpdate.setCurrentBalance(walletToUpdate.getCurrentBalance().subtract(wallet.getCurrentBalance()));
            
            WalletEntity walletEntity = new WalletEntity();
            walletEntity.setId(walletToUpdate.getId());
            walletEntity.setCurrentBalance(walletToUpdate.getCurrentBalance());

            walletRepository.save(walletEntity);

        } else {
            throw new WalletInsuficientFoundsException(wallet.getId());
        }

        return walletToUpdate;
    }
}
