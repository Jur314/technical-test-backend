package com.playtomic.tests.wallet.model;

import java.math.BigDecimal;

import com.playtomic.tests.wallet.entities.WalletEntity;
import com.playtomic.tests.wallet.repository.WalletRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Init the Database H2 with a couple of wallets.
 * 
 * Note: In case you want to load the sql script that is 
 * in resources/data.sql instead of using this class, 
 * modify the properties in application.yml.  
 *
 */
@Configuration
public class LoadDatabase {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    public CommandLineRunner initDatabase(WalletRepository walletRepository) {
        
        return args -> {
            log.info("**** Save a couple of wallets: ****");
            
            WalletEntity walletEntity1 = new WalletEntity();
            WalletEntity walletEntity2 = new WalletEntity();
            WalletEntity walletEntity3 = new WalletEntity();
            WalletEntity walletEntity4 = new WalletEntity();

            walletEntity1.setId(1L);
            walletEntity1.setCurrentBalance(new BigDecimal("2.00"));
            walletEntity2.setId(2L);
            walletEntity2.setCurrentBalance(new BigDecimal("3.00"));
            walletEntity3.setId(3L);
            walletEntity4.setCurrentBalance(new BigDecimal("5.00"));
            walletEntity4.setId(4L);
            walletEntity4.setCurrentBalance(new BigDecimal("7.00"));

            walletRepository.save(walletEntity1);
            walletRepository.save(walletEntity2);
            walletRepository.save(walletEntity3);
            walletRepository.save(walletEntity4);
        };
    }
}
