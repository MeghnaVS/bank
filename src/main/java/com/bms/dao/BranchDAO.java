package com.bms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bms.exception.BankDoesNotExistException;
import com.bms.exception.BranchDoesNotExistException;
import com.bms.exception.DeleteFailedException;
import com.bms.exception.SaveFailedException;
import com.bms.exception.SelectFailedException;
import com.bms.exception.UpdateFailedException;
import com.bms.model.Branch;

@Repository
public class BranchDAO {
	
	JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	//returns the total number of Branches of all the banks 
	public long countAll(){
		return  jdbcTemplate.queryForLong("select count(*) from BRANCH_MASTER");
	}
	
	
	//returns list of all the branch details of all the banks
	public List<Branch> selectAll() throws SelectFailedException {
		try{
			
		return jdbcTemplate.query("select * from BRANCH_MASTER",
				new BranchRowMapper());
		}catch(DataAccessException e){
			throw new SelectFailedException("Branch is not present");			
		}
	}

	//returns count of branches for a particular bank 
	public int countBranchByBank(int bankId) throws BankDoesNotExistException {
		Object args[] = { bankId };
		try {
			return jdbcTemplate.queryForInt("Select count(*) from branch_master where bank_id=?", args);
			
		} catch (DataAccessException e) {
			throw new BankDoesNotExistException("Bank is not present in database");
		}		
	}

	//returns particular branch details based on the branch IFSC code 
	public Branch selectByIfsc(String ifsc) throws BranchDoesNotExistException {
		Object args[] = { ifsc.toUpperCase() };
		try {
			return jdbcTemplate.queryForObject("select * from BRANCH_MASTER where IFSC_CODE=?",args,new BranchRowMapper());
		} catch (DataAccessException e) {
			throw new BranchDoesNotExistException("Branch not present in the database");
		}
	}

	//returns list of branch details based on the bank id of particular bank
	public List<Branch> selectByBankId(int id) throws SelectFailedException {
		Object args[] = { id };
		try {
			return jdbcTemplate.query("Select * from branch_master where bank_id=?", args,new BranchRowMapper());
		} catch (DataAccessException e) {
			throw new SelectFailedException("selection from the data base failed");
		}		
	}

	//updates the branch details of particular bank
	public void update(Branch branch) throws UpdateFailedException {
		Object args[] = { branch.getName().toUpperCase(),
				branch.getAddress().toUpperCase(),
				branch.getStatus().toUpperCase(),
				branch.getIfsc().toUpperCase() };
		try {
			jdbcTemplate.update("update BRANCH_MASTER set NAME=?,ADDRESS=?,STATUS=? where IFSC_CODE=?",args);
		} catch (DataAccessException e) {
			throw new UpdateFailedException("updation failed in the database");
		}
	}

	//creating a new branch for a particular bank and inserting details into it
	public void save(Branch branch) throws SaveFailedException {
		Object args[] = { branch.getIfsc().toUpperCase(),
				branch.getName().toUpperCase(),
				branch.getAddress().toUpperCase(), branch.getStatus(),
				branch.getBankId() };
		try {
			jdbcTemplate.update("insert into BRANCH_MASTER values(?,?,?,?,?)",args);
		} catch (DataAccessException e) {
			throw new SaveFailedException("could not save the branch");
		}
	}
	
	// delete/deactivating the particular branch based on ifsc code provided 
	public int delete(String ifsc) throws DeleteFailedException {
		Object args[] = { ifsc.toUpperCase() };		
		try{
			return jdbcTemplate.update("update BRANCH_MASTER set STATUS='I' where IFSC_CODE=? and status='A'",args);
		}catch(DataAccessException e){
			throw new DeleteFailedException("could not delete particular record");
		}		
	}
	
	//returns list of branch details based on the bank id of particular bank
	public List<Branch> selectAllByBankId(int bankId) throws BankDoesNotExistException{
		try{
			return jdbcTemplate.query("select * from BRANCH_MASTER where BANK_ID=?",new Object[]{bankId},new BranchRowMapper());
		}catch(DataAccessException e){			
			throw new BankDoesNotExistException("Bank does not exist");
		}		
	}
}
