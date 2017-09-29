package com.bms.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.bms.exception.OfferException;
import com.bms.model.Offer;
import com.bms.service.OfferService;

@Controller
public class OfferController {

	@Autowired
	OfferService offerService;
	
	@RequestMapping(value="/showOfferList")
	public ModelAndView showOfferList(Offer offer) throws OfferException{
		
		System.out.println("In showOfferList()");
		List<Offer> offerList=offerService.offerList();
		System.out.println(offerList);
		ModelAndView model = new ModelAndView("offerList");		
		model.addObject("OFFERLIST",offerList);
		return model;
	}
	
	@RequestMapping("/showOfferForm")
	public ModelAndView showOfferForm() {

		Offer offer = new Offer();
		// Default values Here
		offer.setType("Gift");
		offer.setUtilized("N");
		SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MM-yy");
		String d1= simpleDateFormat.format(new Date(System.currentTimeMillis()));
		String d2= simpleDateFormat.format(new Date(System.currentTimeMillis()+459000000));
		
		offer.setOfferDate(d1);
		offer.setExpiryDate(d2);
			
		
		return new ModelAndView("offerForm", "offer", offer);
	}

	@RequestMapping(value = "/offerSubmit", method = RequestMethod.POST)
	public ModelAndView offerDetailSubmit(
			@ModelAttribute @Valid Offer offer, BindingResult bindingResult) {

		ModelAndView modelAndView = new ModelAndView("offerForm");

		if (bindingResult.hasErrors()) {
			return modelAndView;
		}

		try {
			offerService.add(offer);
			modelAndView.addObject("MSG", "Offer saved successfully!");
		} catch (OfferException e) {
			modelAndView.addObject("ERRMSG", e.getMessage());
		}
		return modelAndView;
	}

}
