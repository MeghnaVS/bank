package com.bms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bms.model.Account;

public class AccountRowMapper implements RowMapper<Account>{

	public Account mapRow(ResultSet resultSet, int count) throws SQLException {
		Account a=new Account();
		a.setId(resultSet.getInt(1));
		a.setType(resultSet.getString(2));				
		a.setAmount(resultSet.getInt(3));
		a.setStatus(resultSet.getString(4));
		a.setCustomerId(resultSet.getInt(5));
		
		return a;
	}

}
