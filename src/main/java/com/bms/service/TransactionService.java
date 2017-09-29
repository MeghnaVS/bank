package com.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.dao.AccountDAO;
import com.bms.dao.TransactionDAO;
import com.bms.exception.AccountDoesNotExistException;
import com.bms.exception.LowBalanceException;
import com.bms.exception.TransactionFailedException;
import com.bms.exception.TransactionNotFoundException;
import com.bms.model.Account;
import com.bms.model.Transaction;

@Service
public class TransactionService {

	@Autowired
	TransactionDAO transactionDAO;
	
	@Autowired
	AccountDAO accountDAO;
	
	// method to count the no of transactions
	public int countAll() {
		return transactionDAO.countAll();
	}

	// Method to add transaction in the  account_transaction table 
	public void addTransaction(Transaction transaction) throws TransactionFailedException,
	LowBalanceException,AccountDoesNotExistException {
		
		int amount = 0;
		
		Account account = accountDAO.listByAccountId(transaction.getAccountId());
	
		if(account== null){
			throw new AccountDoesNotExistException("Account with "+ transaction.getAccountId() +" does not exists");
		}
		if (transaction.getType().equals("D")) {

			if (account.getAmount() >= transaction.getAmount())
				amount = account.getAmount() - transaction.getAmount();
			else {
				throw new LowBalanceException("Low Balance..!!");
			}

		} else {
			amount = account.getAmount() + transaction.getAmount();
		}
		account.setAmount(amount);
		accountDAO.update(account);
		transactionDAO.save(transaction);
	}


	// Method to get the list of transaction in the  account_transaction table by accountId
	public List<Transaction> readByAccountId(int accountId) throws TransactionNotFoundException {
		return transactionDAO.selectByAccountId(accountId);
	}

	// Method to get the list of transaction in the  account_transaction table by transaction id
	public List<Transaction> readByTransactionId(int transactionId) throws TransactionNotFoundException {
		return transactionDAO.selectByTransactionId(transactionId);
	}

	// Method to get the list of transaction in the  account_transaction table by Instrument no
	public List<Transaction> readAllTransaction() throws TransactionNotFoundException {
		return transactionDAO.listAccountTransactionTable();
	}

}
