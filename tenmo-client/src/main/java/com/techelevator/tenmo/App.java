package com.techelevator.tenmo;

import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.TransferService;

import java.math.BigDecimal;
import java.util.List;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AccountService accountService = new AccountService(API_BASE_URL);
    private final TransferService transferService = new TransferService(API_BASE_URL);
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);

    private AuthenticatedUser currentUser;

    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        } else {
            transferService.setAuthToken(currentUser.getToken());
        }
    }

    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
                viewTransferDetails();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                handleListUsers();
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            } else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }


    private void viewCurrentBalance() {
        BigDecimal balance = accountService.getBalance(currentUser);
//        TODO need to clean up
        System.out.println(balance);
        // TODO Auto-generated method stub
    }


    private void viewTransferHistory() {
        Transfer[] transfers = transferService.transferHistory();
        if (transfers != null) {
            consoleService.printTransferHistory(transfers);
        } else {
            consoleService.printErrorMessage();
        }
        // TODO Auto-generated method stub
    }


    private void viewTransferDetails() {
        Long getterId = consoleService.getTransferID(); // asks for id and gets the parsed value
        Transfer transferInfo = transferService.getTransferDetails(getterId);
        System.out.println(
                "Id:        " + transferInfo.getTransferId() + "\n" +
                "From:      " + transferInfo.getNameFrom()+ "\n" +
                "To:        " + transferInfo.getNameTo() + "\n" +
                "Type:      " + transferInfo.getTransferTypeDescription(transferInfo.getTransferTypeId()) + "\n" +
                "Status:    " + transferInfo.getTransferStatusDescription(transferInfo.getTransferStatusId()) + "\n" +
                "Amount:    $" + transferInfo.getAmount());
    }

    private void viewPendingRequests() {
        // TODO Auto-generated method stub

    }
    private void sendBucks() {
        // TODO Auto-generated method stub
            Transfer completedTransfer = consoleService.printSendBucks();
            completedTransfer.setUserIdFrom(currentUser.getUser().getId());
            completedTransfer.setTransferStatusId(Transfer.TRANSFER_STATUS_ID);
            completedTransfer.setTransferTypeId(Transfer.TRANSFER_TYPE_ID);
            transferService.sendTransfer(completedTransfer);

    }

    private void handleListUsers() {
        // List all users by userid and username
        User[] users = transferService.listUsers();
        if (users != null) {
            consoleService.printUsers(users);
        } else {
            consoleService.printErrorMessage();
        }
        //TODO clean up later
    }

    private void requestBucks() {
        // TODO Auto-generated method stub

    }

}
