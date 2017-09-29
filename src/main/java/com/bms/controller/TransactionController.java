package com.bms.controller;

import java.util.Collections;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bms.exception.AccountDoesNotExistException;
import com.bms.exception.LowBalanceException;
import com.bms.exception.TransactionFailedException;
import com.bms.exception.TransactionNotFoundException;
import com.bms.model.Account;
import com.bms.model.Customer;
import com.bms.model.Transaction;
import com.bms.service.AccountService;
import com.bms.service.TransactionService;

@Controller
public class TransactionController {

	@Autowired
	TransactionService transactionService;

	@Autowired
	AccountService accountService;
	
	// this add all the account to model attribute 
	@ModelAttribute("ACCOUNTALL")
	public List<Account> allAccounts() {
		ModelAndView modelAndView = new ModelAndView();
		List<Account> accountList = null;
		try {
			accountList = accountService.listAllAccount();

		} catch (AccountNotFoundException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}
		return accountList;
	}

	// this add all the transaction to model attribute 
	@ModelAttribute("TRANSACTIONALL")
	public List<Transaction> allTransactions() {
		ModelAndView modelAndView = new ModelAndView();
		List<Transaction> transactionsList = null;
		try {
			transactionsList = transactionService.readAllTransaction();
		} catch (TransactionNotFoundException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}
		return transactionsList;
	}

	// this is to call the accountTransaction.jsp page
	@RequestMapping("/tranForm")
	public ModelAndView showForm(HttpSession session) {

		Transaction transaction = new Transaction();
		if(session.getAttribute("ACCOUNTID")!= null){
			transaction.setAccountId((Integer)session.getAttribute("ACCOUNTID"));
		}
		return new ModelAndView("accountTransaction", "transaction",
				transaction);
	}

	// this is to check if the transaction is complete or has error... if it has error then it goes to accountTransaction.jsp page if not it goes to transactionList.jsp page
	@RequestMapping(value = "/transactionSubmit", method = RequestMethod.POST)
	public ModelAndView userFormSubmit(
			@ModelAttribute @Valid Transaction transaction,
			BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView("accountTransaction");

		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

		try {
			
			transactionService.addTransaction(transaction);
			modelAndView = new ModelAndView("redirect:transactionList");
			
			modelAndView.addObject("MSG", "Transaction done successfully!");
		} catch (TransactionFailedException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		} catch (LowBalanceException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		} catch (AccountDoesNotExistException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}
		return modelAndView;
	}

	// it gives a acall to Transacvtionlist.jsp page
	@RequestMapping(value = "/transactionList")
	public String getTransactionList(HttpSession session, Model model) {

		List<Account> accountList = null;
		try {

			if (session.getAttribute("USER") != null) {

				Customer customer = (Customer) session
						.getAttribute("USER");
				accountList = accountService.readAccountsByCustomerId(customer
						.getCustomerId());
				session.setAttribute("ACCOUNTLIST", accountList);
				return "transactionList";
			}
		} catch (AccountNotFoundException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		}
		return "transactionList";
	}

	// it shows all the transaction of an account id
	@RequestMapping(value = "/loadTransactions")
	public String getTransactions(@RequestParam("accountId") int accountId,
			HttpSession session, Model model) {

		if (accountId == 0) {
			session.removeAttribute("ACCOUNTID");
			session.removeAttribute("TRANSACTIONLIST");
			model.addAttribute("ERRMSG", "Please Select your Account ID..!!");
			return "transactionList";
		}

		try {
			List<Transaction> transactionsList = transactionService
					.readByAccountId(accountId);
			session.setAttribute("ACCOUNTID", accountId);
			session.setAttribute("TRANSACTIONLIST", transactionsList);
			session.setAttribute("FLAGFORSORT", 0);
		} catch (Exception e) {
			session.setAttribute("ACCOUNTID", accountId);
			session.removeAttribute("TRANSACTIONLIST");
			model.addAttribute("ERRMSG", e.getMessage());
		}
		return "transactionList";
	}

	// it is to sort all the transaction present in the list
	@RequestMapping(value = "/sortTransaction")
	public String sortTransaction(HttpSession session, Model model) {
		
		try {
			
			List<Transaction> transactionsList = (List<Transaction>) session
					.getAttribute("TRANSACTIONLIST");
			

			if (transactionsList.size() == 0) {

				model.addAttribute("ERRMSG", "NO Transactions to Display..!!");
			} else {
				if ((Integer) (session.getAttribute("FLAGFORSORT")) == 0) {

					Collections.reverse(transactionsList);
					session.setAttribute("FLAGFORSORT", 1);

				} else {
					Collections.reverse(transactionsList);
					session.setAttribute("FLAGFORSORT", 0);

				}
				session.setAttribute("TRANSACTIONLIST", transactionsList);
			}
		} catch (Exception e) {

			model.addAttribute("ERRMSG", e.getMessage());
		}
		return "transactionList";
	}
}
