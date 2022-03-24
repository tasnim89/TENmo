package com.techelevator.tenmo.Service;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.IncomingTransfer;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TransferService {

    private final UserDao userDao;
    private final TransferDao transferDao;
    private final AccountDao accountDao;

    public TransferService(UserDao userDao, TransferDao transferDao, AccountDao accountDao) {
        this.userDao = userDao;
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }
    public List<User> userList(String excludedUser) {
        List<User> users = userDao.findAll();
        List<User> filteredUsers = new ArrayList<>();
        for (User user : users) {
            if (!user.getUsername().equals(excludedUser)) {
                filteredUsers.add(user);
            }
        }
        return filteredUsers;
    }


    public Transfer makeTransfer(IncomingTransfer incomingTransfer) {
//        Check to see if valid transfer
        Account fromAccount = accountDao.findAccountByUserId(incomingTransfer.getUserIdFrom());
        Account toAccount = accountDao.findAccountByUserId(incomingTransfer.getUserIdTo());
        BigDecimal toBalance = toAccount.getBalance().add(incomingTransfer.getAmount());
        BigDecimal fromBalance = fromAccount.getBalance().subtract(incomingTransfer.getAmount());
//        if valid transfer money between accounts using account dao
//        if this works create this object
        if (fromAccount != toAccount) {
            if (incomingTransfer.getAmount().compareTo(fromBalance) <= 0) {
                accountDao.updateBalance(incomingTransfer.getUserIdTo(), toBalance);
                accountDao.updateBalance(incomingTransfer.getUserIdFrom(), fromBalance);
                Transfer transfer = new Transfer();
                transfer.setTransferStatusId(incomingTransfer.getTransferStatusId());
                transfer.setTransferTypeId(incomingTransfer.getTransferTypeId());
                transfer.setAmount(incomingTransfer.getAmount());
                Long accountFromId = accountDao.findAccountIdFromUserId(incomingTransfer.getUserIdFrom());
                transfer.setAccountIdFrom(accountFromId);
                Long accountToId = accountDao.findAccountIdFromUserId(incomingTransfer.getUserIdTo());
                transfer.setAccountIdTo(accountToId);
                return transferDao.makeTransfer(transfer);
            }
        }
        return null;

    }

    public Transfer getTransferDetails(Long transferId){
        return transferDao.getTransferDetails(transferId);
    }







}