package com.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.dao.*;
import com.bms.exception.*;
import com.bms.model.*;

@Service
public class CustomerService {
	
	
	CustomerDAO customerDAO;
	
	@Autowired
	public void setCustomerDAO(CustomerDAO customerDAO) {
		this.customerDAO = customerDAO;
	}

	public long countAll(){
		return customerDAO.countAll();
	}
	
	public void add(Customer c) throws CustomerException {

		customerDAO.save(c);
	}
	
	public void update(Customer customer) throws CustomerException {

		customerDAO.update(customer);
	}

	public Customer detailCustomer(long id) throws CustomerException {
		return customerDAO.readById(id);
	}

	public List<Customer> listCustomer() throws CustomerException {
		return customerDAO.readAll();
	}
	
	public List<Customer> listByBankid(int bankId) throws CustomerException {
		return customerDAO.readForBankId(bankId);
	}
	
	public void updateIfsc(String ifsc,int customerId) throws CustomerException {

		customerDAO.updateIfsc(ifsc, customerId);
	}
	
	public int latestCustomerId() throws CustomerException {

		return customerDAO.latestCustomerId();
	}



	public List<Customer> searchCustomer(String searchContent, String customerField) throws CustomerException{
		
		return customerDAO.searchCustomer(searchContent,customerField);
	}
	

}