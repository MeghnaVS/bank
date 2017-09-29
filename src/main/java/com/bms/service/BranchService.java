package com.bms.service;

//import java.util.List;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.dao.BankDAO;
import com.bms.dao.BranchDAO;
import com.bms.exception.BankDoesNotExistException;
import com.bms.exception.BranchDoesNotExistException;
import com.bms.exception.DeleteFailedException;
import com.bms.exception.SaveFailedException;
import com.bms.exception.SelectFailedException;
import com.bms.exception.UpdateFailedException;
import com.bms.model.Bank;
import com.bms.model.Branch;

@Service
public class BranchService {
	@Autowired
	BranchDAO branchDAO;

	@Autowired
	BankDAO bankDAO;

	// branch being added to the branch table
	public void add(Branch branch) throws SaveFailedException,
			BankDoesNotExistException {

		Bank bank;
		bank = bankDAO.selectById(branch.getBankId());
		String ifsc = bank.getName().substring(0, 3).toUpperCase();
		int count = 10000 + branchDAO.countBranchByBank(branch.getBankId()) + 1;
		branch.setIfsc(ifsc + String.valueOf(count));
		branchDAO.save(branch);
	}

	// reads all the branch present in the branch table using branchDAO
	public List<Branch> readAll() throws SelectFailedException {

		return branchDAO.selectAll();
	}

	// returns the branch corresponding to the IFSC passed
	public Branch readByIfcs(String ifsc) throws BranchDoesNotExistException {
		return branchDAO.selectByIfsc(ifsc);
	}

	// returns the list of branch corresponding to the bank with its id
	public List<Branch> readByBankId(int bankId) throws SelectFailedException {
		return branchDAO.selectByBankId(bankId);
	}

	//changes the status of the branch from active to inactive
	public void disable(String ifsc) throws DeleteFailedException,
			BranchDoesNotExistException {

		Branch branch = branchDAO.selectByIfsc(ifsc);
		if (branch.getStatus().equals("I")) {
			throw new DeleteFailedException("Branch already deleted!");
		} else {
			branchDAO.delete(ifsc);
		}

	}

	// updates the branch by the corresponding IFSC
	public void updateByIfsc(Branch branch) throws UpdateFailedException {
		branchDAO.update(branch);
	}

	// returns the list of branch corresponding to the bank with its id
	public List<Branch> readAllByBankId(int bankId)
			throws BankDoesNotExistException {
		return branchDAO.selectAllByBankId(bankId);

	}
}
