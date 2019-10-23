package com.example.demo.business.data.repository;

import com.example.demo.business.data.Program;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface ProgramRepository extends CrudRepository<Program, Long> {
    ArrayList<Program> findByNameContainingIgnoreCase(String s1);
    ArrayList<Program> findAll();

}