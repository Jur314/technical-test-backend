package com.playtomic.tests.wallet.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.playtomic.tests.wallet.model.WalletModel;
import com.playtomic.tests.wallet.service.WalletServices;

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletServices walletServices;

    @RequestMapping("api/")
    public void log() {
        log.info("Logging from api/");
    }

    @GetMapping(
        value="/api/wallets", 
        params = "id"
    )
    public WalletModel getWalletById(@RequestParam("id") Long id) {

        log.info("/api/wallets/".concat(String.valueOf(id)));

        return walletServices.walletInfo(id);
    }

    @PutMapping("api/pay")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<WalletModel> rechargeWallet(@RequestBody WalletModel wallet) {

        log.info("api/pay ");

        WalletModel walletModel = walletServices.pay(wallet);
        walletModel.add( 
            Link.of("/api/pay/{walletId}").withRel(LinkRelation.of("payments"))
                .expand(walletModel.getId()));

        return EntityModel.of(walletModel);
    }

    @PatchMapping("api/charge")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<WalletModel> chargeWallet(@RequestBody WalletModel wallet) {

        log.info("api/charge wallet ");

        WalletModel walletModel = walletServices.updateBalance(wallet);
        walletModel.add( 
            Link.of("/api/charge/{walletId}").withRel(LinkRelation.of("charges"))
                .expand(walletModel.getId()));
        
        return EntityModel.of(walletModel);
    }
}