package com.bms.controller;

import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Null;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bms.exception.DateFormatException;
import com.bms.exception.SelectFailedException;
import com.bms.model.Transaction;
import com.bms.service.SearchService;

@Controller
public class SearchController {

	@Autowired
	SearchService searchService;

	@RequestMapping("/searchTransactions")
	public String search(String transType,String fromDate,String toDate,HttpSession session, Model model) {
		try {

			int accountId = (Integer) session.getAttribute("ACCOUNTID");			
			List<Transaction> list;
			if(transType.toUpperCase().equals("Null")){
				model.addAttribute("ERRMSG","Select search first");
			}
			else if(transType.toUpperCase().equals("TODAY")) {
				list = searchService
						.listCurrentTransaction(accountId);
				session.setAttribute("TRANSACTIONLIST", list);			
			} else if (transType.toUpperCase().equals("DR")) {
				list=searchService.listTransactionByDateRange(accountId, fromDate, toDate);
				session.setAttribute("TRANSACTIONLIST",list);
			}else if(transType.toUpperCase().equals("CREDITS")){
				list=searchService.listTransactionByCredit(accountId);
				session.setAttribute("TRANSACTIONLIST", list);
			}else if(transType.toUpperCase().equals("DEBITS")){
				list=searchService.listTransactionByDebit(accountId);
				session.setAttribute("TRANSACTIONLIST", list);
			}
			
			
		} catch (DateFormatException e) {
			model.addAttribute("ERRMSG",e.getMessage());
		} catch (SelectFailedException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		}catch(NullPointerException e){
			model.addAttribute("ERRMSG","Select search and AccountId first");
		}
		return "transactionList";
	}
}
