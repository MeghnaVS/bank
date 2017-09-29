package com.bms.controller;

import java.util.List;

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

import com.bms.exception.BankDoesNotExistException;
import com.bms.exception.CustomerException;
import com.bms.exception.SelectFailedException;
import com.bms.model.Bank;
import com.bms.model.Branch;
import com.bms.model.Customer;
import com.bms.service.BankService;
import com.bms.service.BranchService;
import com.bms.service.CustomerService;

@Controller
public class CustomerController {

	@Autowired
	BankService bankService;

	@Autowired
	BranchService branchService;

	@Autowired
	CustomerService customerService;

	@ModelAttribute("BANKLIST")
	public List<Bank> allBanks() {
		List<Bank> bankList = null;
		try {
			bankList = bankService.readAll();
		} catch (SelectFailedException e) {
			e.printStackTrace();
		}

		return bankList;
	}

	@RequestMapping("/changeBranch")
	public ModelAndView modifyByBranch(String ifsc, int customerId, int bankId,
			Model model) {

		List<Customer> customerList = null;
		List<Branch> branchList = null;
		Bank bank = null;
		try {
			customerService.updateIfsc(ifsc, customerId);
			model.addAttribute("MSG", "Branch successfully updated!! ");
			customerList = customerService.listByBankid(bankId);
			branchList = branchService.readAllByBankId(bankId);
			bank = bankService.readById(bankId);
		} catch (BankDoesNotExistException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		} catch (CustomerException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		}
		model.addAttribute("CUSTOMERLIST", customerList);
		model.addAttribute("BRANCHLIST", branchList);
		model.addAttribute("BANKDETAIL", bank);

		return new ModelAndView("customerList");

	}

	@RequestMapping("/showCustomerList")
	public ModelAndView listCustomer() {
		return new ModelAndView("customerList");

	}

	@RequestMapping(value = "/loadCustomer")
	public String getCustomers(@RequestParam("bankId") int bankId, Model model,
			Customer customer) {

		List<Customer> customerList = null;
		Bank bank = null;
		List<Branch> branchList = null;

		try {
			customerList = customerService.listByBankid(bankId);
			bank = bankService.readById(bankId);
			branchList = branchService.readAllByBankId(bankId);
		} catch (BankDoesNotExistException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		} catch (CustomerException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		}
		model.addAttribute("CUSTOMERLIST", customerList);
		model.addAttribute("BANKDETAIL", bank);
		model.addAttribute("BRANCHLIST", branchList);

		return "customerList";
	}

	@RequestMapping("/showRegistrationForm")
	public ModelAndView showCustomerForm() {

		ModelAndView modelAndView = new ModelAndView("showRegistrationForm",
				"customer", new Customer());
		return modelAndView;
	}

	@RequestMapping("/showSummaryForm")
	public String showSummaryForm() {
		return "showCustomerSummary";
	}

	@RequestMapping(value = "/loadBranch")
	public String getBranches(@RequestParam("bankId") int bankId, Model model,
			Customer customer) {

		List<Branch> branchList = null;
		Bank bank = new Bank();
		bank.setBankId(bankId);

		System.out.println("In loadBranch()");
		try {
			branchList = branchService.readAllByBankId(bankId);
		} catch (BankDoesNotExistException e) {
			e.printStackTrace();
			model.addAttribute("ERRMSG", e.getMessage());
		}

		model.addAttribute("BANK", bank);
		model.addAttribute("BRANCHLIST", branchList);
		model.addAttribute("customer", customer);
		return "showRegistrationForm";
	}

	@RequestMapping(value = "/customerSubmit", method = RequestMethod.POST)
	public ModelAndView customerFormSubmit(
			@ModelAttribute @Valid Customer customer,
			BindingResult bindingResult, Model model, int bankId, String ifsc) {

		ModelAndView modelAndView = new ModelAndView("showRegistrationForm");

		List<Branch> branchList = null;
		Bank bank = new Bank();
		Branch branch = new Branch();
		try {
			branchList = branchService.readAllByBankId(bankId);
		} catch (BankDoesNotExistException e) {
			e.printStackTrace();
			model.addAttribute("ERRMSG", e.getMessage());
		}

		if (bindingResult.hasErrors()) {

			bank.setBankId(bankId);
			branch.setIfsc(customer.getIfsc());
			model.addAttribute("BANK", bank);
			model.addAttribute("BRANCH", branch);
			model.addAttribute("BRANCHLIST", branchList);
			return modelAndView;
		}
		try {
			customerService.add(customer);
			bank.setBankId(bankId);
			branch.setIfsc(ifsc);
			model.addAttribute("BANK", bank);
			model.addAttribute("BRANCH", branch);
			model.addAttribute("BRANCHLIST", branchList);
			modelAndView.addObject("MSG", "Customer saved successfully!");
		} catch (CustomerException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}

		return modelAndView;
	}

	@RequestMapping(value = "/customerEdit", method = RequestMethod.POST)
	public ModelAndView customerEdit(@ModelAttribute @Valid Customer customer,
			BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView("editCustForm");

		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

		try {
			customerService.update(customer);
			modelAndView.addObject("MSG", "User updated successfully!");
		} catch (CustomerException e) {
			e.printStackTrace();
			modelAndView.addObject("ERRMSG", e.getMessage());
		}

		return modelAndView;
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.POST)
	public ModelAndView editCustomer(int customerId, int bankId, Model model) {
		ModelAndView modelAndView = new ModelAndView("editCustForm");
		Customer customer = null;
		try {
			customer = customerService.detailCustomer(customerId);
		} catch (CustomerException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}

		model.addAttribute("BANKID", bankId);
		model.addAttribute("customer", customer);

		return modelAndView;

	}

	
	@RequestMapping(value = "/searchCustomer", method = RequestMethod.POST)
	public ModelAndView searchCustomer(String searchContent,
			String customerField,  Model model) {
		ModelAndView modelAndView = new ModelAndView("customerList");

		
		try {
			List<Customer> customerlist = customerService.searchCustomer(
					searchContent, customerField);
			model.addAttribute("CUSTOMERLIST", customerlist);
			
			if (customerlist == null) {
				model.addAttribute("ERRMSG", "No record found");
			}
			/*List<String> branchlist = null;
			for (Customer customer : customerlist) {
				branchlist.add(customer.getIfsc());
			}
			model.addAttribute("BRANCHLIST", branchlist);*/
		} catch (CustomerException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		}
		
		
		return modelAndView;

	}

}
