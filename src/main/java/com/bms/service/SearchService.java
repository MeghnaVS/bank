package com.bms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.dao.SearchDAO;
import com.bms.exception.DateFormatException;
import com.bms.exception.SelectFailedException;
import com.bms.model.Transaction;

@Service
public class SearchService {
	@Autowired
	SearchDAO searchDAO;

	public List<Transaction> listCurrentTransaction(int accountId)
			throws SelectFailedException {
		return searchDAO.selectByCurrenDate(accountId);
	}

	public List<Transaction> listTransactionByDateRange(int accountId,
			String fromDate, String toDate) throws SelectFailedException,DateFormatException {
		try{
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd");
			Date fDate = simpleDateFormat.parse(fromDate);
			Date tDate = simpleDateFormat.parse(toDate);
			return searchDAO.selectByDateRange(accountId, fDate, tDate);
		}catch(ParseException e){
			throw  new DateFormatException("Date format entered is wrong");
		}		
	}

	public List<Transaction> listTransactionByCredit(int accountId)
			throws SelectFailedException {
		return searchDAO.selectByCredit(accountId);

	}

	public List<Transaction> listTransactionByDebit(int accountId)
			throws SelectFailedException {

		return searchDAO.selectByDebit(accountId);

	}

}
