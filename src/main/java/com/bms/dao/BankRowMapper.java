package com.bms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bms.model.Bank;


/*This is row mapper for the bank module and this will convert each row of the 
 * database into a bank object and will return it .This throws an SQLException 
 * which is handeled in the corresponding dao
 */
public class BankRowMapper implements RowMapper<Bank>{
	
	public Bank mapRow(ResultSet resultSet, int count) throws SQLException {
		Bank bank=new Bank();
		bank.setBankId(resultSet.getInt(1));
		bank.setName(resultSet.getString(2));
		bank.setStrength(resultSet.getInt(3));
		bank.setType(resultSet.getString(4));
		return bank;
	}

}
