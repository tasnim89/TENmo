package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.Service.TransferService;
import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.IncomingTransfer;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class TransferController {

    private final AccountDao accountDao;
    private final UserDao userDao;
    private final TransferDao transferDao;
    private final TransferService transferService;


    public TransferController(AccountDao accountDao, UserDao userDao, TransferService transferService, TransferDao transferDao) {
        this.accountDao = accountDao;
        this.userDao = userDao;
        this.transferService = transferService;
        this.transferDao = transferDao;
    }


    @RequestMapping(path = "listAll", method = RequestMethod.GET)
    public List<User> userList(Principal principal) {
        return transferService.userList(principal.getName());
    }

    @RequestMapping(path = "account/", method = RequestMethod.GET)
    public User findUserNameByAccountId(Long id){return userDao.findUserNameByAccountId(id);

    }
    @RequestMapping(path = "transfers", method = RequestMethod.POST)
    public Transfer makeTransfer(@RequestBody IncomingTransfer transfer) {
        return transferService.makeTransfer(transfer);
    }

    @RequestMapping(path = "transferHistory", method = RequestMethod.GET)
    public List<Transfer> findTransfers(Principal principal) {
        Long userId = userDao.findIdByUsername(principal.getName());
        return transferDao.findAllTransfers(userId);
    }
    @RequestMapping(path = "transfer/{transferId}", method = RequestMethod.GET)
    public Transfer getTransferDetails(@PathVariable Long transferId){
        return transferService.getTransferDetails(transferId);
    }
}

