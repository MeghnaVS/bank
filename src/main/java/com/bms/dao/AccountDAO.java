package com.bms.dao;

import java.util.DuplicateFormatFlagsException;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bms.exception.AccountDoesNotExistException;
import com.bms.exception.AccountExistsException;


import com.bms.model.Account;

@Repository
public class AccountDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	//Returns all the accounts in the table
	public List<Account> listAllAccount()throws AccountNotFoundException{
		List<Account> account=jdbcTemplate.query("select * from account_master",new AccountRowMapper());
		if(account.size()==0){
			throw new AccountNotFoundException("Account Not Present!!");
		}else
			return account;
		}
	
	//Returns total number of accounts in account_master table
	public long countAll(){
		return  jdbcTemplate.queryForLong("select count(*) from account_master");
	}
	
	
	//Returns list of accounts for the matching account id passed 
	public Account listByAccountId(int id) throws AccountDoesNotExistException{
		Object args[]={id};
		try{
		return jdbcTemplate.queryForObject("select * from account_master where account_id=?",
				new AccountRowMapper(),args);
		}catch(EmptyResultDataAccessException e){
			e.printStackTrace();
			throw new AccountDoesNotExistException("Account Not Available");
		}
	}
	
	//Returns list of accounts for the matching account id passed
	public List<Account> listByCustomerId(int id) throws AccountNotFoundException{
		Object args[] = { id };
		try {
			return jdbcTemplate.query(
					"select * from account_master where customer_id=?",
					new AccountRowMapper(), args);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			throw new AccountNotFoundException("No Account Found for Customer - "+id);
		}
	}
	
	
	//Returns list of Customer and Branch with same ifsc code
	public List<Account> selectJoinOfCustomerBranch(String ifsc) {
		Object args[] = { ifsc };
		try {
			return jdbcTemplate
					.query("select * from account_master where customer_id in( select customer_id from customer_master where ifsc_code=?)",
							new AccountRowMapper(), args);
		} catch (EmptyResultDataAccessException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	//Updates the account status to inactive
	public void update(Account account) {
		Object args[] = {account.getId() };
		jdbcTemplate
				.update("update account_master set status='I' where account_id=?", args);
	}
	
	//Adds new account to the table
	public void save(Account account) throws AccountExistsException,
			AccountDoesNotExistException {
		Object args[] = { account.getType(), account.getAmount(),
				account.getCustomerId() };
		try {
			jdbcTemplate
					.update("insert into account_master(type,amount,customer_id) values(?,?,?)",
							args);
		} catch (DuplicateFormatFlagsException exception) {
			exception.printStackTrace();
			throw new AccountExistsException(
					"Account already exists...Please Re-Enter Correct Detials!");
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			throw new AccountDoesNotExistException("Customer ID Not Found");
		}
	}
}
