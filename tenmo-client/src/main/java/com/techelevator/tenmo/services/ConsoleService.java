package com.techelevator.tenmo.services;


import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);
    private AuthenticatedUser currentUser;



    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public Long promptForInt(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
            }
        }
    }

    public BigDecimal promptForBigDecimal(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return new BigDecimal(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a decimal number.");
            }
        }
    }


    public void printUsers(User[] users) {
        System.out.println("--------------------------------------------");
        System.out.println("TEnmo users");
        System.out.println("--------------------------------------------");
        for (User user : users) {
            System.out.println(user.getId() + ": " + user.getUsername());
        }
        System.out.println("--------------------------------------------");
    }

    public Transfer printSendBucks(){
        Transfer transfer = new Transfer();
        Long userIdTo = promptForInt("Enter ID of user you are sending to (0 to cancel): ");
        BigDecimal amount = promptForBigDecimal("Enter amount: " );
        transfer.setUserIdTo(userIdTo);
        transfer.setAmount(amount);
        return transfer;
    }

    public void printTransferHistory (Transfer[] transfers) {

        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Transfer History");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println(String.format("%-2s %10s %15s %32s","TransferID","From","To","Amount"));
        System.out.println("-----------------------------------------------------------------------");
        for (Transfer transfer : transfers) {
            if (transfer.getTransferStatusId() == 2){
                System.out.println(String.format("%-2s %16s %18s %30s",transfer.getTransferId(), "From:" + transfer.getNameFrom(),"To:" + transfer.getNameTo(),"$" + transfer.getAmount()));
            }
        }
    }

    public Long getTransferID(){
        Long transferId = promptForInt( "Please enter transfer ID to view details (0 to cancel): ");
        return transferId;
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

}
