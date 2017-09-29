package com.bms.dao;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bms.exception.SelectFailedException;
import com.bms.model.Transaction;

@Repository
public class SearchDAO {

	JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long countAllById(int accountId) throws SelectFailedException{
		try{
			Object args[]={accountId};
			return jdbcTemplate.queryForLong("Select count(*) from account_transaction where account_id=?",args); 
		}catch(DataAccessException e ){
			throw new SelectFailedException("Counting all record failed!");
		}
	}
	public List<Transaction> selectByCurrenDate(int accountId)
			throws SelectFailedException {
		List<Transaction> list;
		Object args[] = { accountId };
		try {
			list = jdbcTemplate
					.query("select * from account_transaction where trunc(trn_date)=trunc(SYSDATE) and account_id=?",
							args, new TransactionRowMapper());
			return list;
		} catch (Exception e) {
			throw new SelectFailedException(
					"Listing of the transaction failed!");
		}
	}
	
	public List<Transaction> selectByDateRange(int accountId,Date fromDate,Date toDate)
			throws SelectFailedException {
		List<Transaction> list;
		Object args[] = {accountId,fromDate,toDate};
		try {
			list = jdbcTemplate.query("select * from account_transaction where account_id=? and trunc(trn_date) between trunc(?) and trunc(?)",args, new TransactionRowMapper());
			return list;
		} catch (Exception e) {
			throw new SelectFailedException(
					"Listing of the transaction failed!");
		}
	}
	public List<Transaction> selectByCredit(int accountId)throws SelectFailedException {
		List<Transaction> list;
		Object args[] = {accountId };
		try {
			list = jdbcTemplate
					.query("select * from account_transaction where account_id=? and trn_type='C' ",args, new TransactionRowMapper());
			return list;
		} catch (Exception e) {
			throw new SelectFailedException("Listing of the transaction failed!");
		}
	}
	public List<Transaction> selectByDebit(int accountId)throws SelectFailedException {
		List<Transaction> list;
		Object args[] = {accountId };
		try {
			list = jdbcTemplate
					.query("select * from account_transaction where account_id=? and trn_type='D' ",args, new TransactionRowMapper());
			return list;
		} catch (Exception e) {
			throw new SelectFailedException("Listing of the transaction failed!");
		}
	}
}
