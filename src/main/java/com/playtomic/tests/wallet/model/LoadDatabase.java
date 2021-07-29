package com.playtomic.tests.wallet.model;

import java.math.BigDecimal;

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
            log.info("Preloading " + walletRepository.save(new WalletModel(new BigDecimal("2.00"))));
            log.info("Preloading " + walletRepository.save(new WalletModel(new BigDecimal("3.00"))));
            log.info("Preloading " + walletRepository.save(new WalletModel(new BigDecimal("5.00"))));
            log.info("Preloading " + walletRepository.save(new WalletModel(new BigDecimal("7.00"))));
        };
    }
}
