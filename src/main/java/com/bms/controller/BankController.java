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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.bms.service.BankService;
import com.bms.service.BranchService;
import com.bms.exception.BankDoesNotExistException;
import com.bms.exception.SaveFailedException;
import com.bms.exception.SelectFailedException;
import com.bms.model.Bank;
import com.bms.model.Branch;

@Controller
@SessionAttributes
public class BankController {

	BankService bankService;
	BranchService branchService;

	// auto wiring the branch service with the controller
	@Autowired
	public void setBranchService(BranchService branchService) {
		this.branchService = branchService;
	}

	// auto wiring the bank service with the controller
	@Autowired
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	@ModelAttribute("BANKLIST")
	public List<Bank> allBanks() {
		List<Bank> bankList = null;
		try {
			bankList = bankService.readAll();
			return bankList;
		} catch (SelectFailedException e) {
			System.out.print("Listing of all bank list failed");
		}
		return bankList;
	}

	@RequestMapping("/displayform")
	public ModelAndView display() {
		ModelAndView modelAndView = new ModelAndView("bankForm");
		try {
			List<Bank> list = bankService.readAll();
			return new ModelAndView("display", "bank", list);
		} catch (SelectFailedException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}
		return modelAndView;

	}

	// mapping the bank form with the call from the show method
	@RequestMapping("/bankform")
	public ModelAndView showForm() {

		Bank bank = new Bank();
		return new ModelAndView("bankForm", "bank", bank);
	}

	// after the submit of the form the user will see the page
	@RequestMapping(value = "/bankSubmit", method = RequestMethod.POST)
	public ModelAndView bankFormSubmit(@ModelAttribute @Valid Bank bank,
			BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView("bankForm");

		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

		try {
			bankService.add(bank);
			modelAndView.addObject("SUCCESSMSG", "Bank saved successfully!");
		} catch (SaveFailedException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}
		return modelAndView;
	}

	// populating the bank master form

	// Mapping to display method
	@RequestMapping("/bankmaster")
	public ModelAndView displaySearchForm() {
		return new ModelAndView("bankMasterDetail");
	}

	@RequestMapping("/displaybranch")
	public String displaySearchResult(int bankId, Model model) {
		if (bankId != 0) {
			try {
				Bank bank = bankService.readById(bankId);
				List<Branch> list = branchService.readByBankId(bankId);
				model.addAttribute("BRANCHLIST", list);
				model.addAttribute("BANKDETAIL", bank);
			} catch (BankDoesNotExistException e) {
				model.addAttribute("ERRMSG", e.getMessage());
			} catch (SelectFailedException e) {
				model.addAttribute("ERRMSG", e.getMessage());
			}
		}
		return "bankMasterDetail";
	}

}
