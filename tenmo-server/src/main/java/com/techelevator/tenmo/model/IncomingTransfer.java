package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class IncomingTransfer {
    public static final int TRANSFER_STATUS_ID = 2;
    public static final int TRANSFER_TYPE_ID = 2;
    private Long userIdFrom;
    private Long userIdTo;
    private BigDecimal amount;
    private Integer transferStatusId;
    private Integer transferTypeId;

    public IncomingTransfer(Long userIdFrom, Long userIdTo, BigDecimal amount) {
        this.userIdFrom = userIdFrom;
        this.userIdTo = userIdTo;
        this.amount = amount;
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
}


