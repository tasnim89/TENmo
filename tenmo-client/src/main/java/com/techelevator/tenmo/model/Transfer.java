package com.techelevator.tenmo.model;

import io.cucumber.java.sl.In;

import java.math.BigDecimal;

public class Transfer {


    private Long transferId;
    public static final int TRANSFER_STATUS_ID = 2;
    public static final int TRANSFER_TYPE_ID = 2;
    public String transferStatusDescription;
    public String transferTypeDescription;
    private Long userIdFrom;
    private Long userIdTo;
    private Long accountIdFrom;
    private Long accountIdTo;
    private BigDecimal amount;
    private Integer transferStatusId;
    private Integer transferTypeId;
    private String nameFrom;
    private String nameTo;
    public String getTransferStatusDescription(Integer transferStatusId){
        if (transferStatusId == 2){
           return "Approved";
        } else if (transferStatusId == 1){
            return "Pending";
        } else{
            return "Rejected";
        }
    }

    public void setTransferStatusDescription() {
        this.transferStatusDescription = transferStatusDescription;
    }



    public String getTransferTypeDescription(Integer transferTypeId) {
        if (transferTypeId == 2){
            return "Send";
        } else {
            return "Request";
        }
    }

    public void setTransferTypeDescription(String transferTypeDescription) {
        this.transferTypeDescription = transferTypeDescription;
    }


    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getUserIdFrom() {
        return userIdFrom;
    }

    public void setUserIdFrom(Long userIdFrom) {
        this.userIdFrom = userIdFrom;
    }

    public Long getUserIdTo() {
        return userIdTo;
    }

    public void setUserIdTo(Long userIdTo) {
        this.userIdTo = userIdTo;
    }

    public Long getAccountIdFrom() {
        return accountIdFrom;
    }

    public void setAccountIdFrom(Long accountIdFrom) {
        this.accountIdFrom = accountIdFrom;
    }

    public Long getAccountIdTo() {
        return accountIdTo;
    }

    public void setAccountIdTo(Long accountIdTo) {
        this.accountIdTo = accountIdTo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Integer transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public Integer getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Integer transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getNameFrom() {
        return nameFrom;
    }

    public void setNameFrom(String nameFrom) {
        this.nameFrom = nameFrom;
    }

    public String getNameTo() {
        return nameTo;
    }

    public void setNameTo(String nameTo) {
        this.nameTo = nameTo;
    }
}


