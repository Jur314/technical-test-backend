package com.playtomic.tests.wallet;

import java.math.BigDecimal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.playtomic.tests.wallet.api.WalletController;
import com.playtomic.tests.wallet.model.WalletModel;
import com.playtomic.tests.wallet.service.WalletInsuficientFoundsException;
import com.playtomic.tests.wallet.service.WalletNotFoundException;
import com.playtomic.tests.wallet.service.WalletServices;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(WalletController.class)
public class WalletControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WalletServices walletServices;

    @Test
    public void givenWalletId_whenGetWallet_thenReturnJsonArray() throws Exception {
        
        long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("3.0");

        WalletModel wallet = new WalletModel();
        wallet.setId(walletId);
        wallet.setCurrentBalance(currentBalance);
        wallet.add(linkTo(
            methodOn(WalletController.class)
            .getWalletById(walletId))
            .withSelfRel());

        Mockito.when(walletServices.walletInfo(walletId)).thenReturn(wallet);

        mockMvc.perform(
                get("/api/wallets")
                    .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(wallet.getId()))
                .andExpect(jsonPath("$.currentBalance").value(wallet.getCurrentBalance()))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    public void givenWalletIdNotExist_whenGetWallet_thenReturnException() throws Exception {
        long walletId = 11L;
        WalletModel wallet = new WalletModel();
        wallet.setId(walletId);

        Mockito.when(walletServices.walletInfo(wallet.getId()))
            .thenThrow(new WalletNotFoundException(wallet.getId()));

        mockMvc.perform(
                get("/api/wallets")
                    .param("id", "11"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find wallet " + wallet.getId()));
    }

    @Test
    public void rechargeWallet_thenReturnJsonArray() throws Exception {
        
        long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("10.0");

        WalletModel wallet = new WalletModel();
        wallet.setId(walletId);
        wallet.setCurrentBalance(currentBalance);
        wallet.add(linkTo(
            methodOn(WalletController.class)
            .getWalletById(walletId))
            .withSelfRel());

        Mockito.when(walletServices.pay(wallet)).thenReturn(wallet);

        mockMvc.perform(
                put("/api/pay")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(wallet)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(wallet.getId()))
                .andExpect(jsonPath("$.currentBalance").value(wallet.getCurrentBalance()))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    public void rechargeWallet_thenReturnException() throws Exception {
        
        long walletId = 11L;
        BigDecimal currentBalance = new BigDecimal("10.0");

        WalletModel wallet = new WalletModel();
        wallet.setId(walletId);
        wallet.setCurrentBalance(currentBalance);

        Mockito.when(walletServices.pay(wallet))
            .thenThrow(new WalletNotFoundException(wallet.getId()));

        mockMvc.perform(
                put("/api/pay")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(wallet)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Could not find wallet " + wallet.getId()));
    }

    @Test
    public void chargeWallet_thenReturnJsonArray() throws Exception {
        
        long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("1.0");

        WalletModel wallet = new WalletModel();
        wallet.setId(walletId);
        wallet.setCurrentBalance(currentBalance);
        wallet.add(linkTo(
            methodOn(WalletController.class)
            .getWalletById(walletId))
            .withSelfRel());

        Mockito.when(walletServices.updateBalance(wallet)).thenReturn(wallet);

        mockMvc.perform(
                patch("/api/charge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(wallet)))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/hal+json"))
                .andExpect(jsonPath("$.id").value(wallet.getId()))
                .andExpect(jsonPath("$.currentBalance").value(wallet.getCurrentBalance()))
                .andExpect(jsonPath("$._links").exists());
    }

    @Test
    public void chargeWallet_thenReturnExceptionWalletNotFound() throws Exception {
        
        long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("1.0");

        WalletModel wallet = new WalletModel();
        wallet.setId(walletId);
        wallet.setCurrentBalance(currentBalance);

        Mockito.when(walletServices.updateBalance(wallet))
            .thenThrow(new WalletNotFoundException(wallet.getId()));

        mockMvc.perform(
                patch("/api/charge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(wallet)))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Could not find wallet " + wallet.getId()));
    }

    @Test
    public void chargeWallet_thenReturnExceptionInsuficientFounds() throws Exception {
        
        long walletId = 1L;
        BigDecimal currentBalance = new BigDecimal("3.0");

        WalletModel wallet = new WalletModel();
        wallet.setId(walletId);
        wallet.setCurrentBalance(currentBalance);

        Mockito.when(walletServices.updateBalance(wallet))
            .thenThrow(new WalletInsuficientFoundsException(wallet.getId()));

        mockMvc.perform(
                patch("/api/charge")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(asJsonString(wallet)))
                    .andExpect(status().isNotFound())
                    .andExpect(content().string("Operation Cancelled, Insufiecient founds for wallet " + wallet.getId()));
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
