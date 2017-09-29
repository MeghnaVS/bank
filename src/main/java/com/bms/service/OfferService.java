package com.bms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.dao.*;
import com.bms.exception.*;
import com.bms.model.*;

@Service
public class OfferService {
	
	@Autowired
	OfferDAO offerDAO;
	
	public long countAll(){
		return offerDAO.countAll();
	}
	
	
	public void add(Offer offer) throws OfferException {

		// validation logic

		offerDAO.save(offer);
	}


	public List<Offer> offerList() throws OfferException {

		// validation logic

		return offerDAO.readAll();
	}

}