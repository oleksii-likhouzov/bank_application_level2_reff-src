package org.test.bankapp.exception;


import org.test.bankapp.model.CheckingAccount;

public class OverDraftLimitExceededException extends NotEnoughFundsException {
    private CheckingAccount account;
    private float withdraft;

    public OverDraftLimitExceededException(CheckingAccount account, float withdraft) {
        this.account = account;
        this.withdraft = withdraft;
    }

    public float getBalance() {
        return account.getBalance();
    }

    public float getOverdraft() {
        return account.getOverdraft();
    }

    public float getWithdraft() {
        return withdraft;
    }
}

