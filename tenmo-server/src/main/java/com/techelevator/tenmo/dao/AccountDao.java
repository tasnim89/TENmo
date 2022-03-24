package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDao {

    Account findAccountByUserId(Long id);

    Long findAccountIdFromUserId(Long userId);

    void updateBalance(Long userIdTo, BigDecimal toBalance);
}
