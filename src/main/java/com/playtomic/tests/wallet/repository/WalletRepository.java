package com.playtomic.tests.wallet.repository;

import com.playtomic.tests.wallet.entities.WalletEntity;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A CRUD Repository to operate with a wallet.
 *
 */
public interface WalletRepository extends JpaRepository<WalletEntity, Long> {
   
}
