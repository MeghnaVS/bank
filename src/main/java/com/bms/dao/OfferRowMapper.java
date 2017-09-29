package com.bms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.bms.model.*;

public class OfferRowMapper implements RowMapper<Offer>{

	
	public Offer mapRow(ResultSet resultSet, int count) throws SQLException {
		Offer offer = new Offer();
		offer.setId(resultSet.getInt(1));
		offer.setType(resultSet.getString(2));
		
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
		String d1= simpleDateFormat.format(resultSet.getDate(3));
		String d2= simpleDateFormat.format(resultSet.getDate(4));
	
		offer.setOfferDate(d1);				
		offer.setExpiryDate(d2);
		
		offer.setUtilized(resultSet.getString(5));	
		offer.setCustomerId(resultSet.getInt(6));
		return offer;
	}

}


