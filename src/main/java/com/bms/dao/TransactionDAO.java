package com.bms.dao;

import org.springframework.jdbc.BadSqlGrammarException;

import java.util.DuplicateFormatFlagsException;
import java.util.List;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bms.exception.LowBalanceException;
import com.bms.exception.TransactionFailedException;
import com.bms.exception.TransactionNotFoundException;
import com.bms.model.Transaction;

@Repository
public class TransactionDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	// Method to count the no of transaction in the  account_transaction table
	public int countAll() {
		return jdbcTemplate.queryForInt("select count(*) from account_transaction");
	}
	// Method to list the all the transaction in the  account_transaction table
	public List<Transaction> listAccountTransactionTable() throws TransactionNotFoundException {
		List<Transaction> transaction=jdbcTemplate.query("select * from account_transaction",new TransactionRowMapper());
		if(transaction.size()==0){
			throw new TransactionNotFoundException("Transaction Not Present in Table");
		}else
			return transaction;
		}
	// Method to get the list of transaction in the  account_transaction table by accountId
	public List<Transaction> selectByAccountId(int id) throws TransactionNotFoundException {
		Object args[] = { id };
		List<Transaction> transactions=jdbcTemplate.query(
				"select * from account_transaction where account_id=?",
				new TransactionRowMapper(), args);
		if(transactions.size()==0){
			throw new TransactionNotFoundException("No Transaction For User Account");
		}else
		return transactions;
	}
	
	// Method to get the list of transaction in the  account_transaction table by transactionId
	public List<Transaction> selectByTransactionId(int id) throws TransactionNotFoundException {
		Object args[] = { id };
		List<Transaction> transactions=jdbcTemplate.query(
				"select * from account_transaction where trn_id=?",
				new TransactionRowMapper(), args);
		if(transactions.size()==0){
			throw new TransactionNotFoundException("No Transaction For This Transaction ID");
		}else
		return transactions;
	}
	
	// Method to get the list of transaction in the  account_transaction table by Instrument no
	public List<Transaction> selectByInstrument(int id) throws TransactionNotFoundException {
		Object args[] = { id };
		List<Transaction> transactions=jdbcTemplate.query(
				"select * from account_transaction where instrument_number=?",
				new TransactionRowMapper(), args);
		if(transactions.size()==0){
			throw new TransactionNotFoundException("No Transaction For This Instrument NO");
		}else
		return transactions;	
	}

	// method to save transaction in the account_transaction table
	public void save(Transaction transaction)
			throws TransactionFailedException, LowBalanceException {

		Object args[] = {transaction.getType(),
				transaction.getAmount(), transaction.getInstrumentNo(),
				transaction.getDescription(), transaction.getAccountId() };

		try {
			jdbcTemplate
					.update("insert into account_transaction(trn_type,amount,instrument_number,description,account_id) values(?,?,?,?,?)",
							args);

		} catch (DuplicateFormatFlagsException exception) {
			exception.printStackTrace();
			throw new TransactionFailedException("Transaction Cannot be Saved!");
		} catch (BadSqlGrammarException e) {
			e.printStackTrace();
			throw new TransactionFailedException("Transaction Cannot be Saved!");
		}

	}

	
}
