package com.bms.service;

//import java.util.List;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.dao.BankDAO;
import com.bms.exception.BankDoesNotExistException;
import com.bms.exception.SaveFailedException;
import com.bms.exception.SelectFailedException;
import com.bms.exception.UpdateFailedException;
import com.bms.model.Bank;

@Service
public class BankService {

	@Autowired
	BankDAO bankDAO;

	/*
	 * counts and returns the number of banks present in the bank table
	 */
	public long countAll() {
		return bankDAO.countAll();
	}

	// adds a bank to the bank table
	public void add(Bank bank) throws SaveFailedException {
		bankDAO.save(bank);
	}

	/*
	 * returns the list of all the bank present in the bank table
	 */
	public List<Bank> readAll() throws SelectFailedException {
		return bankDAO.selectAll();
	}

	// returns the details of bank for the corresponding BANKID
	public Bank readById(int id) throws BankDoesNotExistException {
		Bank bank = bankDAO.selectById(id);
		return bank;
	}

	// updates a bank taking a bank as parameter
	public void modify(Bank bank) throws UpdateFailedException {
		bankDAO.update(bank);
	}

	/*
	 * returns the list of banks corresponding to the
	 *  type of the bank
	 */
	public List<Bank> readByType(String type) throws BankDoesNotExistException {
		List<Bank> list = bankDAO.selectByType(type);
		return list;
	}
}
