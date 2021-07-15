package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Country;
import com.example.demo.repo.CountryRepo;

@Service
public class CountryService {

	@Autowired
	CountryRepo cRepo;
	
	public Country readCountry(int id) {
		return cRepo.findById(id).get();
	}
	
	public List<Country> readCountryList() {
		return cRepo.findAll();
	}
	
	public void saveCountry(Country c) {
		cRepo.save(c);
	}
	
	public void deleteCountry(int id) {
		cRepo.deleteById(id);
	}
	
}
