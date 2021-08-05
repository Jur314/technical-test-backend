package com.playtomic.tests.wallet.assemblers;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.stereotype.Component;

import com.playtomic.tests.wallet.api.WalletController;
import com.playtomic.tests.wallet.entities.WalletEntity;
import com.playtomic.tests.wallet.model.WalletModel;


@Component
public class WalletModelAssembler extends RepresentationModelAssemblerSupport<WalletEntity, WalletModel> {

    public WalletModelAssembler() {
        super(WalletController.class, WalletModel.class);
    }

    @Override
    public WalletModel toModel(WalletEntity walletEntity) {

        WalletModel walletModel = instantiateModel(walletEntity);

        walletModel.add(linkTo(
            methodOn(WalletController.class)
            .getWalletById(walletEntity.getId()))
            .withSelfRel());

        walletModel.setId(walletEntity.getId());
        walletModel.setCurrentBalance(walletEntity.getCurrentBalance());

        return walletModel;
    }
    
}

