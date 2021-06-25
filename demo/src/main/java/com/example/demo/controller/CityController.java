package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.City;
import com.example.demo.repository.CityRepository;

@RestController
@RequestMapping("/api")
public class CityController {

  @Autowired
  CityRepository CityRepository;

	/*
	 * @GetMapping("/getcity") public ResponseEntity<List<City>>
	 * getAllTutorials(@RequestParam(required = false) String title) { try {
	 * List<City> city = new ArrayList<City>();
	 * 
	 * if (city.isEmpty()) { return new ResponseEntity<>(HttpStatus.NO_CONTENT); }
	 * 
	 * return new ResponseEntity<>(city, HttpStatus.OK); } catch (Exception e) {
	 * return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); } }
	 */
  
  @GetMapping("/city")
  public ResponseEntity<List<City>> getAllTutorials(@RequestParam(required = false) String name) {
    try {
      List<City> city = new ArrayList<City>();

      if (name == null)
        CityRepository.findAll().forEach(city::add);
      else
    	  CityRepository.findByName(name).forEach(city::add);

      if (city.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(city, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/city/{id}")
  public ResponseEntity<City> getTutorialById(@PathVariable("id") long id) {
    Optional<City> tutorialData = CityRepository.findById(id);

    if (tutorialData.isPresent()) {
      return new ResponseEntity<>(tutorialData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/savecity")
  public ResponseEntity<City> createTutorial(@RequestBody City city) {
    try {
      City _city = CityRepository.save(new City(city.getName()));
      return new ResponseEntity<>(_city, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/city/{id}")
  public ResponseEntity<City> updateTutorial(@PathVariable("id") long id, @RequestBody City tutorial) {
    Optional<City> tutorialData = CityRepository.findById(id);

    if (tutorialData.isPresent()) {
      City _city = tutorialData.get();
      _city.setName(tutorial.getName());
      return new ResponseEntity<>(CityRepository.save(_city), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/city/{id}")
  public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
    try {
      CityRepository.deleteById(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/city")
  public ResponseEntity<HttpStatus> deleteAllTutorials() {
    try {
      CityRepository.deleteAll();
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

}