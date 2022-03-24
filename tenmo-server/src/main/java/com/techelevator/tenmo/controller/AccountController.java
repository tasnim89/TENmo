package com.techelevator.tenmo.controller;


import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.UserDao;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private final AccountDao accountDao;
    private final UserDao userDao;

    public AccountController(AccountDao accountDao, UserDao userDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @RequestMapping(path = "/balance" , method = RequestMethod.GET)
    public BigDecimal getBalance(Principal principal){
        Long userId = userDao.findIdByUsername(principal.getName());
        return accountDao.findAccountByUserId(userId).getBalance();
    }

    @RequestMapping(path = "account/{id}" , method = RequestMethod.GET)
    public Long getAccountIdFromUserId(Long userId){
        Long accountId =  accountDao.findAccountIdFromUserId(userId);
        return accountId;
    }

}
