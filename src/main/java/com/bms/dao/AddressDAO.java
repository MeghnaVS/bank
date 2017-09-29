package com.bms.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bms.exception.AddressException;
import com.bms.model.Address;

@Repository
public class AddressDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;

	public long countAll() throws AddressException {
		try {
			return jdbcTemplate
					.queryForLong("select count(*) from customer_address");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new AddressException("Customer table can't be accessed!!");
		}
	}

	public void save(Address address) throws AddressException {

		Object args[] = { address.getStreet(), address.getCity(),
				address.getState(), address.getCustomerId() };
		try {
			jdbcTemplate.update(
					"insert into customer_address values (?,?,?,?)", args);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new AddressException("Customer address cannot be saved!!");
		}
	}

	public void remove(int id) throws AddressException {

		Object args[] = { id };
		try {
			jdbcTemplate
					.update("delete* from customer_address where customer_id = ?",
							args);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new AddressException("Customer address cannot be removed!!");
		}
	}

	public void update(Address address) throws AddressException {

		Object args[] = { address.getStreet(), address.getCity(),
				address.getState(), address.getCustomerId() };
		try {
			jdbcTemplate
					.update("update customer_address set street = ?, city = ?,state = ? where customer_id = ?",
							args);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new AddressException("Customer address cannot be updated!!");
		}
	}

	public List<Address> readAll() throws AddressException {
		try {
			return jdbcTemplate.query("select * from customer_address",
					new AddressRowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new AddressException("Customer address  list cannot be fetched!!");
		}

	}

	public Address readById(int id) throws AddressException {
		Object args[] = { id };
		try {
			return jdbcTemplate.queryForObject(
					"select * from customer_address where customer_id=?",
					new AddressRowMapper(), args);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new AddressException("Customer address cannot be fetched!!");
		}

	}

}