package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class JdbcAccountDao implements AccountDao{

    private JdbcTemplate jdbcTemplate;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Account findAccountByUserId(Long id) {
        String sql = "SELECT * FROM account WHERE user_id = ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, id);
        if (rowSet.next()){
            return mapRowToAccount(rowSet);
        }
//        TODO throw appropriate exception
        throw new UsernameNotFoundException("User " + id + " was not found.");

    }

    public Long findAccountIdFromUserId(Long userId){
        String sql = "SELECT account_id FROM account WHERE user_id = ?;";
       Long accountId = jdbcTemplate.queryForObject(sql, Long.class, userId);
        return accountId;
    }




    public void updateBalance(Long userId, BigDecimal amount){
        String sql = "UPDATE account SET balance = ? WHERE user_id = ?;";
         jdbcTemplate.update(sql, amount, userId);
    }


    private Account mapRowToAccount(SqlRowSet rs) {
       Account account = new Account();
        account.setAccountId(rs.getInt("account_id"));
        account.setUserId(rs.getInt("user_id"));
        account.setBalance(BigDecimal.valueOf(rs.getDouble("balance")));
        return account;
    }
}
