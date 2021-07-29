package com.playtomic.tests.wallet.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.playtomic.tests.wallet.model.WalletModel;
import com.playtomic.tests.wallet.service.WalletServices;

@RestController
public class WalletController {
    private Logger log = LoggerFactory.getLogger(WalletController.class);

    @Autowired
    private WalletServices walletServices;

    @RequestMapping("/")
    public void log() {
        log.info("Logging from /");
    }

    @GetMapping("/wallet")
    public EntityModel<WalletModel> getWallet(@RequestParam Long id) {

        Link link = linkTo(WalletController.class).withSelfRel();
        log.info("Accesing from /wallet to wallet " + id );

        return EntityModel.of(walletServices.walletInfo(id), link);
    }

    @PutMapping("/pay")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<WalletModel> rechargeWallet(@RequestBody WalletModel wallet) {

        Link link = linkTo(WalletController.class).withSelfRel();
        log.info("/pay ");

        return EntityModel.of(walletServices.pay(wallet), link);
    }

    @PatchMapping("/charge")
    @ResponseStatus(HttpStatus.OK)
    public EntityModel<WalletModel> chargeWallet(@RequestBody WalletModel wallet) {

        Link link = linkTo(WalletController.class).withSelfRel();
        log.info("/charge wallet ");
        
        return EntityModel.of(walletServices.updateBalance(wallet), link);
    }
}
