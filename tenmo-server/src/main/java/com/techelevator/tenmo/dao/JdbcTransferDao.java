package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao implements TransferDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Transfer getTransferDetails(Long transferId){
        Transfer transferInfo = new Transfer();
        String sql = "SELECT t.* ,(SELECT tu1.username AS from_name WHERE t.account_from = a.account_id), (SELECT tu2.username AS to_name WHERE t.account_to = b.account_id)\n" +
                "FROM transfer t\n" +
                "JOIN account a ON t.account_from = a.account_id\n" +
                "JOIN account b ON t.account_to = b.account_id\n" +
                "JOIN tenmo_user tu1 ON tu1.user_id = a.user_id\n" +
                "JOIN tenmo_user tu2 ON tu2.user_id = b.user_id\n" +
                "WHERE transfer_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);
            while(results.next()) {
                transferInfo = mapRowToTransfer(results);
            }
            return transferInfo;
        }
        catch (DataAccessException e){
            return null;
        }

    }
    @Override
    public List<Transfer> findAllTransfers(Long userId) {

        List<Transfer> transfers = new ArrayList<>();
        String sql = "SELECT t.* ,(SELECT tu1.username AS from_name WHERE t.account_from = a.account_id), (SELECT tu2.username AS to_name WHERE t.account_to = b.account_id)\n" +
                "FROM transfer t\n" +
                "JOIN account a ON t.account_from = a.account_id\n" +
                "JOIN account b ON t.account_to = b.account_id\n" +
                "JOIN tenmo_user tu1 ON tu1.user_id = a.user_id\n" +
                "JOIN tenmo_user tu2 ON tu2.user_id = b.user_id\n" +
                "WHERE a.user_id = ? OR b.user_id = ? ;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql,userId, userId);
        while(results.next()) {
            Transfer transfer = mapRowToTransfer(results);
            transfers.add(transfer);
        }
        return transfers;
    }

    public Transfer makeTransfer(Transfer transfer){
        String sql = "INSERT INTO transfer VALUES (DEFAULT, 2, 2, ?, ?, ?) RETURNING transfer_id";
        try {
            return jdbcTemplate.queryForObject(sql, Transfer.class, transfer.getAccountIdFrom(), transfer.getAccountIdTo(),
                    transfer.getAmount());

        } catch (DataAccessException e) {
           return null;
        }
    }
    private Transfer mapRowToTransfer(SqlRowSet rs) {
        Transfer transfer = new Transfer();
        transfer.setTransferId(rs.getLong("transfer_id"));
        transfer.setTransferStatusId(rs.getInt("transfer_status_id"));
        transfer.setTransferTypeId(rs.getInt("transfer_type_id"));
        transfer.setAccountIdFrom(rs.getLong("account_from"));
        transfer.setAccountIdTo(rs.getLong("account_to"));
        transfer.setAmount(BigDecimal.valueOf(rs.getDouble("amount")));
        transfer.setNameFrom(rs.getString("from_name"));
        transfer.setNameTo(rs.getString("to_name"));
        return transfer;
    }
}
