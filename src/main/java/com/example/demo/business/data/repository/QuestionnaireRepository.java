package com.example.demo.business.data.repository;


import com.example.demo.business.data.Questionnaire;
import com.example.demo.business.data.User;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface QuestionnaireRepository extends CrudRepository<Questionnaire, Long> {
    Questionnaire findByUser(User u);


}
