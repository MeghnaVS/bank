package com.bms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bms.exception.BankDoesNotExistException;
import com.bms.exception.SaveFailedException;
import com.bms.exception.SelectFailedException;
import com.bms.exception.UpdateFailedException;
import com.bms.model.Bank;

@Repository
public class BankDAO {
	JdbcTemplate jdbcTemplate;

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long countAll() {
		return jdbcTemplate.queryForLong("select count(*) from bank_master");
	}

	/*
	 * Selecting the list of all the bank present in the data base And the
	 * method is throwing the SelectFailed exception
	 */
	public List<Bank> selectAll() throws SelectFailedException {

		try {
			List<Bank> list = jdbcTemplate.query("select * from bank_master",
					new BankRowMapper());
			return list;
		} catch (DataAccessException e) {
			throw new SelectFailedException("Listing of the bank failed !");
		}
	}

	/*
	 * This method is selecting the list of the detail of the bank by a
	 * particular id provided by user and this method throws
	 * BankDoesNotExitException
	 */
	public Bank selectById(int id) throws BankDoesNotExistException {
		Object args[] = { id };

		try {
			Bank bank = jdbcTemplate.queryForObject(
					"select * from bank_master where bank_id=?",
					new BankRowMapper(), args);
			return bank;
		} catch (DataAccessException e) {
			throw new BankDoesNotExistException(
					"Bank not exist for the given id!");
		}

	}

	/*
	 * This method is updating the bank with the particular id and same method
	 * to update any time of the details of a particular bank at a time
	 */

	public void update(Bank bank) throws UpdateFailedException {
		Object args[] = { bank.getName(), bank.getStrength(), bank.getType(),
				bank.getBankId() };
		try {
			jdbcTemplate
					.update("update bank_master set name=?,strength=?,type=? where bank_id=?",
							args);
		} catch (DataAccessException e) {
			throw new UpdateFailedException("Update not possible for the bank!");
		}

	}

	/*
	 * This method is for adding a new bank and this method throws
	 * SaveFailedException
	 */
	public void save(Bank bank) throws SaveFailedException {
		Object args[] = { bank.getName().toUpperCase(), bank.getStrength(),
				bank.getType().toUpperCase() };
		try {
			jdbcTemplate
					.update("insert into bank_master(name,strength,type) values(?,?,?)",
							args);
		} catch (DataAccessException e) {
			throw new SaveFailedException("New Bank addition failed!!");
		}
	}

	/*
	 * This method used to select all the list of bank by their type and throws
	 * BankDoesNotExistException
	 */
	public List<Bank> selectByType(String type)
			throws BankDoesNotExistException {
		Object args[] = { type };

		try {
			List<Bank> list = jdbcTemplate.query(
					"select * from bank_master where type=?",
					new BankRowMapper(), args);
			return list;
		} catch (DataAccessException e) {
			throw new BankDoesNotExistException(
					"No banks for the selected type");
		}
	}

}
