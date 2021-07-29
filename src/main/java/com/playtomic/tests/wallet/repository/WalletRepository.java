package com.playtomic.tests.wallet.repository;

import java.util.Optional;

import com.playtomic.tests.wallet.model.WalletModel;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * A CRUD Repository to operate with a wallet.
 *
 */
public interface WalletRepository extends CrudRepository<WalletModel, Long> {
    Optional<WalletModel> findById(@Param("id") Long id);  
}
