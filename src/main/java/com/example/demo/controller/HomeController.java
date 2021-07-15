package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Country;
import com.example.demo.service.CountryService;

@Controller
public class HomeController {

	@Autowired
	CountryService cService;
	
	@RequestMapping("/")
	public String welcome() {
		return "index";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		Country c = new Country();
		model.addAttribute("country", c);
		return "add_country";
	}
	
	@PostMapping("/add")
	public String addSubmit(@Valid @ModelAttribute("country") Country c, BindingResult br) {
		if (br.hasErrors())
			return "add_country";
		else {
			cService.saveCountry(c);
			return "redirect:/list_countries";
		}
	}
	
	@RequestMapping("/list_countries")
	public String listCountries(Model model) {
		List<Country> list = cService.readCountryList();
		model.addAttribute("countries", list);
		return "country";
	}
	
	@RequestMapping("/list_details/{id}")
	public ModelAndView listDetails(@PathVariable(name="id") int id) {
		ModelAndView mav = new ModelAndView("detail");
		Country c = cService.readCountry(id);
		mav.addObject("country", c);
		return mav;
	}
	
	@RequestMapping("/edit/{id}")
	public ModelAndView edit(@PathVariable(name="id") int id) {
		ModelAndView mav = new ModelAndView("edit_country");
		Country c = cService.readCountry(id);
		mav.addObject("country", c);
		return mav;
	}
	
	@RequestMapping(value="/save", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("country") Country c, BindingResult br) {
		if (br.hasErrors())
			return "edit_country";
		else {
			cService.saveCountry(c);
			return "redirect:/list_countries";
		}
	}
	
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable(name="id") int id) {
		cService.deleteCountry(id);
		return "redirect:/list_countries";
	}
}
