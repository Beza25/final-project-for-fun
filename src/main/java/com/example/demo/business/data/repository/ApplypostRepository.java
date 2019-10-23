package com.example.demo.business.data.repository;
import com.example.demo.business.data.Applypost;
import com.example.demo.business.data.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;


public interface ApplypostRepository extends CrudRepository<Applypost, Long>
{

        Iterable<Applypost> findAllByOrderByPostedDateTimeDesc();
        ArrayList<Applypost> findAll();
        ArrayList<Applypost> findByUser(User u);

}