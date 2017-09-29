package com.bms.service;

import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.dao.*;
import com.bms.exception.AccountDoesNotExistException;
import com.bms.exception.AccountExistsException;
import com.bms.exception.TransactionNotFoundException;
import com.bms.model.*;

/**
 * 
 * @author Ritesh
 * @version 1.0
 * @since 0.1
 *
 */
@Service
public class AccountService {
	
	@Autowired
	AccountDAO accountDAO;
	
	//calls countAll() to get total number of accounts available
	public long countAll(){
		return accountDAO.countAll();
	}
	
	//calls save() to add new account to the table
	public void add(Account account) throws AccountExistsException,
	AccountDoesNotExistException {
		accountDAO.save(account);
	}
	
	//calls modify() to deactivate the account
	public void modify(Account account) {
		accountDAO.update(account);
	}

	//calls listByAccountId() to get all accounts available by matching accountId
	public Account readByAccountId(int accountId) throws AccountDoesNotExistException{
		return accountDAO.listByAccountId(accountId);
	}
	
	//calls listByCustomerId() to get all accounts available by matching Customer Id
	public List<Account> readAccountsByCustomerId(int customerId) throws AccountNotFoundException{
		return accountDAO.listByCustomerId(customerId);
	}

	//calls listAllAccount() to get all accounts available
	public List<Account> listAllAccount() throws AccountNotFoundException{
		return accountDAO.listAllAccount();
	}
	
	//calls readJoinOfCustomerBranch() to get list of Customer and Branch with same ifsc code
	public List<Account> readJoinOfCustomerBranch(String ifsc) {
		return accountDAO.selectJoinOfCustomerBranch(ifsc);
	}
	
	//used to calculate the opening amount for each account
	public int openingAmt(int accountId)throws AccountDoesNotExistException, TransactionNotFoundException {
		int amount = 0;
		TransactionService tds = new TransactionService();
		List<Transaction> list = tds.readByAccountId(accountId);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getType() == "C")
				amount += list.get(i).getAmount();
			else
				amount -= list.get(i).getAmount();
		}
		AccountDAO accountDAO = new AccountDAO();
		Account account = accountDAO.listByAccountId(accountId);
		amount += account.getAmount();
		return amount;
	}

}
