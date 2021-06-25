package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
  List<City> findByName(String name);
}