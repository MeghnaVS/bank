package com.bms.dao;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bms.model.Transaction;

public class TransactionRowMapper implements RowMapper<Transaction>{

	
	public Transaction mapRow(ResultSet resultSet, int count) throws SQLException {
		Transaction t=new Transaction();
		t.setId(resultSet.getInt(1));
		t.setTrnDate(resultSet.getDate(2));
		t.setType(resultSet.getString(3));
		t.setAmount(resultSet.getInt(4));
		t.setInstrumentNo(resultSet.getInt(5));
		t.setDescription(resultSet.getString(6));				
		t.setAccountId(resultSet.getInt(7));				
		return t;
	}

}
