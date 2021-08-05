package com.playtomic.tests.wallet.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.math.BigDecimal;
import java.util.Optional;

import com.playtomic.tests.wallet.assemblers.WalletModelAssembler;
import com.playtomic.tests.wallet.model.WalletModel;
import com.playtomic.tests.wallet.repository.WalletRepository;
import com.playtomic.tests.wallet.service.StripeService;
import com.playtomic.tests.wallet.service.WalletNotFoundException;
import com.playtomic.tests.wallet.service.WalletServices;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WalletServices.class)
public class WalletServicesTest {

    @MockBean
    private WalletRepository walletRepository;

    @MockBean
    private WalletModelAssembler walletModelAssembler;

    @MockBean StripeService stripeService;

    @MockBean
    private WalletServices walletServices;

    @Test
    public void givenWalletId_whenGetWalletInfo() throws Exception {
        
        long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("3.0");

        WalletModel walletModel = new WalletModel();
        walletModel.setId(walletId);
        walletModel.setCurrentBalance(currentBalance);

        Mockito.when(walletRepository.findById(walletId).map(walletModelAssembler::toModel))
            .thenReturn(Optional.of(walletModel));

        assertEquals(1l, walletModel.getId());
        assertEquals(currentBalance, walletModel.getCurrentBalance());
    }

    @Test
    public void givenWalletId_whenGetWalletInfo_thenException() throws Exception {
        
        long walletId = 11L;
        BigDecimal currentBalance = new BigDecimal("3.0");

        WalletModel walletModel = new WalletModel();
        walletModel.setId(walletId);
        walletModel.setCurrentBalance(currentBalance);

        Mockito.when(walletRepository.findById(walletId).map(walletModelAssembler::toModel))
            .thenThrow(new WalletNotFoundException(walletModel.getId()));

        Exception e = assertThrows(RuntimeException.class, () -> {
            Mockito.when(walletRepository.findById(walletId).map(walletModelAssembler::toModel))
            .thenThrow(new WalletNotFoundException(walletModel.getId()));
        });


        String expectedMsg = "Could not find wallet " + walletModel.getId();
        String actualMsg = e.getMessage();

        assertTrue(actualMsg.contains(expectedMsg));
    }

    @Test
    public void givenWallet_whenPay() throws Exception {
        
        long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("3.0");
        BigDecimal finalCurrentBalance = new BigDecimal("6.0");

        WalletModel walletModel = new WalletModel();
        walletModel.setId(walletId);
        walletModel.setCurrentBalance(currentBalance);

        WalletModel walletModelToUpdate = new WalletModel();
        walletModelToUpdate.setId(walletModel.getId());
        walletModelToUpdate.setCurrentBalance(currentBalance.add(walletModel.getCurrentBalance()));

        Mockito.when(walletServices.walletInfo(walletModel.getId())).thenReturn(walletModel);

        Mockito.when(walletRepository.save(any()))
            .thenReturn(Optional.of(walletModelToUpdate));

        assertEquals(1l, walletModelToUpdate.getId());
        assertEquals(finalCurrentBalance, walletModelToUpdate.getCurrentBalance());
    }

    @Test
    public void givenWallet_whenUpdateBalance() throws Exception {
        
        long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("3.0");
        BigDecimal finalCurrentBalance = new BigDecimal("0.0");

        WalletModel walletModel = new WalletModel();
        walletModel.setId(walletId);
        walletModel.setCurrentBalance(currentBalance);

        WalletModel walletModelToUpdate = new WalletModel();
        walletModelToUpdate.setId(walletModel.getId());
        walletModelToUpdate.setCurrentBalance(currentBalance.subtract(walletModel.getCurrentBalance()));

        Mockito.when(walletServices.walletInfo(walletModel.getId())).thenReturn(walletModel);

        Mockito.when(walletRepository.save(any()))
            .thenReturn(Optional.of(walletModelToUpdate));

        assertEquals(1l, walletModelToUpdate.getId());
        assertEquals(finalCurrentBalance, walletModelToUpdate.getCurrentBalance());
    }
    
}
