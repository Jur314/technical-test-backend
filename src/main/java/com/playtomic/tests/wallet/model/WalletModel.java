package com.playtomic.tests.wallet.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A Wallet.
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "wallets", itemRelation = "wallet")
@JsonInclude(Include.NON_NULL)
public class WalletModel extends RepresentationModel<WalletModel>{
    
    private long id;
    private BigDecimal currentBalance;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }
    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

}
