package com.example.demo.business.data.repository;

import com.example.demo.business.data.Applypost;
import com.example.demo.business.data.Org;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface OrgRepository extends CrudRepository<Org, Long> {

    ArrayList<Org> findByNameContainingIgnoreCase(String s1);
    ArrayList<Org> findAll();

}
