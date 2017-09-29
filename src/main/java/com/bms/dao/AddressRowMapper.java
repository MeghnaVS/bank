package com.bms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bms.model.*;

public class AddressRowMapper implements RowMapper<Address>{

	
	public Address mapRow(ResultSet resultSet, int count) throws SQLException {
		Address address = new Address();
		address.setStreet(resultSet.getString(1));
		address.setCity(resultSet.getString(2));				
		address.setState(resultSet.getString(3));
		address.setCustomerId(resultSet.getInt(4));	
		return address;
	}

}


