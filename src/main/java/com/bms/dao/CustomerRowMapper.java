package com.bms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bms.model.*;

public class CustomerRowMapper implements RowMapper<Customer>{

	
	public Customer mapRow(ResultSet resultSet, int count) throws SQLException {
		Customer customer = new Customer();
		customer.setCustomerId(resultSet.getInt(1));
		customer.setCustomerName(resultSet.getString(2));	
		customer.setPassword(resultSet.getString(3));
		customer.setAge(resultSet.getInt(4));
		customer.setPhone(resultSet.getLong(5));				
		customer.setEmail(resultSet.getString(6));				
		customer.setType(resultSet.getString(7));
		customer.setIfsc(resultSet.getString(8));
		return customer;
	}

}


