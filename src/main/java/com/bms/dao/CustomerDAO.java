package com.bms.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Repository;

import com.bms.exception.*;
import com.bms.model.*;

@Repository
public class CustomerDAO {

	/* @Autowired */
	JdbcTemplate jdbcTemplate;

	@Autowired
	public void setjdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public long countAll() {
		return jdbcTemplate
				.queryForLong("select count(*) from customer_master");
	}

	public void save(Customer customer) throws CustomerException {

		int customer_id = 0;

		Object args[] = { customer.getCustomerName(), customer.getPassword(),
				customer.getAge(), customer.getPhone(), customer.getEmail(),
				customer.getType(), customer.getIfsc() };

		try {
			System.out.println(customer);
			jdbcTemplate
					.update("insert into customer_master(name,password,age,phone,email,type,ifsc_code) values (?,?,?,?,?,?,?)",
							args);

		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer can't be saved!!");

		}
		try {
			customer_id = jdbcTemplate
					.queryForInt("select max(customer_id) from customer_master");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer address can't be saved!!");
		}
		Object args1[] = { customer.getAddress().getStreet(),
				customer.getAddress().getCity(),
				customer.getAddress().getState(), customer_id };
		try {
			jdbcTemplate
					.update("insert into customer_address (street,city,state,CUSTOMER_ID) values (?,?,?,?)",
							args1);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer address can't be saved!!");
		}
	}

	public void remove(long id) throws CustomerException {

		Object args[] = { id };
		try {
			jdbcTemplate.update(
					"delete from customer_master where customer_id = ?", args);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer can't be removed!!");

		}
	}

	public void update(Customer customer) throws CustomerException {

		Object args[] = { customer.getCustomerName(), customer.getPassword(),
				customer.getAge(), customer.getPhone(), customer.getEmail(),
				customer.getType(), customer.getIfsc(),
				customer.getCustomerId() };
		Object args1[] = { customer.getAddress().getStreet(),
				customer.getAddress().getCity(),
				customer.getAddress().getState(), customer.getCustomerId() };
		try {
			jdbcTemplate
					.update("update customer_master set name = ?, password = ?,age = ?,phone = ?,email = ?,type = ?,ifsc_code = ? where customer_id = ?",
							args);
			jdbcTemplate
					.update("update customer_address set street = ?, city = ?,state = ? where customer_id = ?",
							args1);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer can't be updated!!");

		}
	}

	public void updateIfsc(String ifsc, int customerId)
			throws CustomerException {

		Object args[] = { ifsc, customerId };
		try {
			jdbcTemplate
					.update("update customer_master set ifsc_code = ? where customer_id = ?",
							args);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer's branch can't be updated!!");

		}
	}

	public List<Customer> readAll() throws CustomerException {
		try {
			return jdbcTemplate.query("select * from customer_master",
					new CustomerRowMapper());
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer list can't be retrieved!!");

		}
	}

	public Customer readById(long id) throws CustomerException {
		Object args[] = { id };
		try {
			Customer customer = jdbcTemplate.queryForObject(
					"select * from customer_master where customer_id=?",
					new CustomerRowMapper(), args);

			if (customer != null) {
				Address address = jdbcTemplate.queryForObject(
						"select * from customer_address where customer_id=?",
						new AddressRowMapper(), args);
				customer.setAddress(address);
			}

			return customer;
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("No Customer found with provided ID!");
		}
	}

	public List<Customer> readForBankId(int bankId) throws CustomerException {
		Object args[] = { bankId };
		try {
			return jdbcTemplate
					.query("select * from customer_master where ifsc_code in( select ifsc_code from branch_master where bank_id=?)",
							new CustomerRowMapper(), args);
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer list cant't be fetched!!");

		}
	}

	public int latestCustomerId() throws CustomerException {

		try {
			return jdbcTemplate
					.queryForInt("select max(CUSTOMER_ID) from customer_master");
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Latest customerId can't be retrieved!!");

		}
	}

	public List<Customer> searchCustomer(String searchContent,
			String customerField) throws CustomerException {
		int age;
		long phone;
		try {
			if (customerField.equals("age")) {
				age = Integer.parseInt(searchContent);
				Object args[] = { age };

				return jdbcTemplate.query(
						"select * from customer_master where age = ?",
						new CustomerRowMapper(), args);
			} else if (customerField.equals("phone")) {
				phone = Long.parseLong(searchContent);
				Object args[] = { phone };
				return jdbcTemplate.query(
						"select * from customer_master where phone = ?",
						new CustomerRowMapper(), args);

			} else {
				Object args[] = { customerField, searchContent };
				return jdbcTemplate.query(
						"select * from customer_master where ? = ?",
						new CustomerRowMapper(), args);

			}
		} catch (DataAccessException e) {
			e.printStackTrace();
			throw new CustomerException("Customer Not present");
		}
	}

}