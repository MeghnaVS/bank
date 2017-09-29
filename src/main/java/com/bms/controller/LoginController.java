package com.bms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.bms.exception.CustomerException;
import com.bms.model.Customer;
import com.bms.service.CustomerService;

@Controller
public class LoginController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(value = "/login")
	public ModelAndView loginCustomer() {
		return new ModelAndView("login", "customer", new Customer());

	}
	
	@RequestMapping(value = "/logout")
	public String logout(HttpSession session,Model model) {
		session.invalidate();
		model.addAttribute("MSG","User logout successfully!");
		return "login";
	}

	@RequestMapping(value = "/validateLogin")
	public String validateCustomer(int customerId, String password,
			Model model, HttpSession httpSession) {

		try {
			Customer customer = customerService.detailCustomer(customerId);

			if (customer.getPassword().equals(password)) {
				httpSession.setAttribute("USER", customer);
						
				if(customer.getCustomerId()==5037){
					return ("redirect:bankmaster");
				}else{
					return ("redirect:transactionList");
				}
			} else {
				model.addAttribute("ERRMSG",
						"Incorrect CustomerId/Password, Please try again!!");
				return ("login");
			}
		}catch (CustomerException e) {
			model.addAttribute("ERRMSG", e.getMessage());
		}catch (Exception e) {
			model.addAttribute("ERRMSG", "Connection to the Database Failed");
		}
		return ("login");
	}

	
}
