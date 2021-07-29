package com.playtomic.tests.wallet.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.hateoas.RepresentationModel;

/**
 * A Wallet.
 *
 */
@Entity
public class WalletModel extends RepresentationModel<WalletModel>{
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private BigDecimal currentBalance;

    public WalletModel() {}

    public WalletModel(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((currentBalance == null) ? 0 : currentBalance.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        WalletModel other = (WalletModel) obj;
        if (currentBalance == null) {
            if (other.currentBalance != null)
                return false;
        } else if (!currentBalance.equals(other.currentBalance))
            return false;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Wallet [currentBalance=" + currentBalance + ", id=" + id + "]";
    }
}
