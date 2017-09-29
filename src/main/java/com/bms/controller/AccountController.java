package com.bms.controller;


import java.util.List;

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
import com.bms.exception.AccountExistsException;
import com.bms.exception.BankDoesNotExistException;
import com.bms.exception.SelectFailedException;
import com.bms.model.Account;
import com.bms.model.Bank;
import com.bms.model.Branch;
import com.bms.model.Customer;
import com.bms.service.*;

@Controller
public class AccountController {
	@Autowired
	AccountService accountService;
	
	@Autowired
	BankService bankService;
	
	@Autowired
	BranchService branchService;
	
	//Used for populating the BankList for accountList.jsp
	@ModelAttribute("BANKLIST")
	public List<Bank> allBanks() throws SelectFailedException {
		List<Bank> bankList; 
		try{
			bankList= bankService.readAll();
			return bankList;
		}catch(SelectFailedException e){
			throw new SelectFailedException("Listing of bank failed !");
		}
	}

	//Used for displaying information in accountSummary.jsp
	@RequestMapping("/accountSummary")	
	public ModelAndView showForm(HttpSession session){

		if(session.getAttribute("ACCOUNTID")!= null){
			int id=(Integer)session.getAttribute("ACCOUNTID");
			try {
				Account account=accountService.readByAccountId(id);
				return new ModelAndView("accountSummary","account",account);
			} catch (AccountDoesNotExistException e) {
				e.printStackTrace();
			}
		}
		
		return new ModelAndView("accountSummary","account",new Account());
	}

	//Displays the accountDetails.jsp
	@RequestMapping("/showForm")	
	public ModelAndView showForm(){
		
		Account account=new Account();
		return new ModelAndView("accountDetails","account",account);
	}
	
	//Used for showForm for adding the account from acountDetails
	@RequestMapping(value="/accountSubmit",method=RequestMethod.POST)	
	public ModelAndView userFormSubmit(@ModelAttribute @Valid Account account,BindingResult bindingResult){
		
		
		ModelAndView modelAndView=new ModelAndView("accountDetails");
		
		if(bindingResult.hasErrors()){
			return modelAndView;			
		}
		try {
			accountService.add(account);
			modelAndView.addObject("MSG", "User saved successfully!");
		} catch (AccountExistsException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}catch ( AccountDoesNotExistException e){
			modelAndView.addObject("ERRMSG", e.getMessage());
		}
	
		return modelAndView;
	}
	
	//Used from transactionList.jsp to load all accounts
	@RequestMapping(value = "/loadAccount")
	public ModelAndView getAccount(@RequestParam("ifsc_code") String ifsc,HttpSession session, Model model) {
		ModelAndView modelAndView=new ModelAndView("accountList");
		Branch branch=new Branch();
		branch.setIfsc(ifsc);
		List<Account> accounts = accountService.readJoinOfCustomerBranch(ifsc.trim());
		if(accounts.size()==0){
			modelAndView.addObject("ERRMSG", "No ACCOUNT for SELECTED BRANCH!!");
		}
		session.setAttribute("ACCOUNTLIST", accounts);
		session.setAttribute("BRANCH", branch);
		
		return modelAndView;
	}

	//Displays the accountList.jsp
	@RequestMapping("/showAccountList")	
	public String showAccountList(){
		return "accountList";
	}
	//Used for deleting an account from accountList.jsp
	@RequestMapping(value = "/loadAllBranch")
	public ModelAndView getBranches(@RequestParam("bankId") int bankId, Model model,HttpSession session,
			Customer customer) throws BankDoesNotExistException {
		ModelAndView modelAndView=new ModelAndView("accountList");
		Bank bank=new Bank();
		bank.setBankId(bankId);
		List<Branch> branchList = branchService.readAllByBankId(bankId);
		if(branchList.size()==0){
			modelAndView.addObject("ERRMSG", "No Branch for SELECTED Bank!!");
			session.removeAttribute("ACCOUNTLIST");
		}
		
		session.setAttribute("BRANCHLIST", branchList);
		session.setAttribute("BANK", bank);
		return modelAndView;
	}
	
	//Used for deleting an account from accountList.jsp
	@RequestMapping("/deleteAccount")
	public String deleteAccount(@RequestParam("accountId") int id, Model model) {
		System.out.println("IN delete account" + id);
		Account account;
		
		try {
			account=accountService.readByAccountId(id);
			accountService.modify(account);
			model.addAttribute("MSG","Account will be inactive the next time you login");
		}catch (AccountDoesNotExistException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		}

		System.out.println("IN delete account" + id);
		System.out.println("in model and view mapping");
		return "accountList";
	}

}
