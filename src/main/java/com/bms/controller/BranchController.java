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
import org.springframework.web.servlet.ModelAndView;

import com.bms.exception.BankDoesNotExistException;
import com.bms.exception.BranchDoesNotExistException;
import com.bms.exception.DeleteFailedException;
import com.bms.exception.SaveFailedException;
import com.bms.exception.SelectFailedException;
import com.bms.exception.UpdateFailedException;
import com.bms.model.Bank;
import com.bms.model.Branch;
import com.bms.service.BankService;
import com.bms.service.BranchService;

@Controller
public class BranchController {
	
	@Autowired
	BankService bankService;
		
	@Autowired
	BranchService branchService;
	
	//Will  return all banks available in database
	@ModelAttribute("BANKLIST")
	public List<Bank> allBanks(){
		 
		try{
			List<Bank> bankList=bankService.readAll();
			return bankList;
		}catch(SelectFailedException e){
			return null;	
		}
		
	}
	
	// mapping the branch form with the call from the show method
	@RequestMapping("/branchform")
	public ModelAndView showForm() {
		return new ModelAndView("branchForm","branch",new Branch());		 
	}

	// after the submit of the form the user will see the page
	@RequestMapping(value = "/branchSubmit", method = RequestMethod.POST)
	public ModelAndView branchFormSubmit(@ModelAttribute @Valid Branch branch,BindingResult bindingResult,HttpSession session,Model model) {

		
		ModelAndView modelAndView = new ModelAndView("branchForm");
		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

		try{
			branchService.add(branch);
			modelAndView = new ModelAndView("redirect:bankmaster");
			Bank bank=bankService.readById(branch.getBankId());
			model.addAttribute("BANKDETAIL", bank);
			modelAndView.addObject("SUCCESSMSG","Branch saved successfully!");
		} catch (SaveFailedException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		} catch (BankDoesNotExistException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}
		return modelAndView;
	}
	@RequestMapping("/deletebranch")
	public String deleteBranch(String ifsc,Model model,int bankId) {
		  
		try{
			branchService.disable(ifsc);
			model.addAttribute("SUCCESSMSG","Branch deletion success !!");
			model.addAttribute("BRANCHLIST", branchService.readByBankId(bankId));
			model.addAttribute("BANKDETAIL", bankService.readById(bankId));
		}catch(DeleteFailedException e){
			model.addAttribute("ERRMSG","Branch deletion failed !!");
			try{							
				model.addAttribute("BRANCHLIST", branchService.readByBankId(bankId));
				model.addAttribute("BANKDETAIL", bankService.readById(bankId));
				}catch(SelectFailedException e1){
					model.addAttribute("ERRMSG",e1.getMessage());
				}catch (BankDoesNotExistException e2) {
					model.addAttribute("ERRMSG",e2.getMessage());
				}
		}catch (BankDoesNotExistException e) {
			model.addAttribute("ERRMSG",e.getMessage());
		}catch (SelectFailedException e) {
			model.addAttribute("ERRMSG",e.getMessage());
		}catch (BranchDoesNotExistException e) {
			model.addAttribute("ERRMSG",e.getMessage());
		}
		return "bankMasterDetail";
	}	
	@RequestMapping("/editbranch")
	public ModelAndView editBranch(Branch branch,Model model){
		return  new ModelAndView("branchEdit","BRANCH",branch);
		
	}
	
	
	@RequestMapping("/updatebranch")
	public String updateBranch(Branch branch,Model model){		
		try{
			branchService.updateByIfsc(branch);
			model.addAttribute("SUCCESSMSG","Branch updation success !!");			
			model.addAttribute("BRANCHLIST",branchService.readByBankId(branch.getBankId()));
			model.addAttribute("BANKDETAIL",bankService.readById(branch.getBankId()));
		}catch(UpdateFailedException e){
			model.addAttribute("ERRMSG",e.getMessage());
		}catch(SelectFailedException e){
			model.addAttribute("ERRMSG",e.getMessage());
		}catch(BankDoesNotExistException e){
			model.addAttribute("ERRMSG",e.getMessage());
		}
	return "bankMasterDetail";
	}	
}
