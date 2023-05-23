package com.stay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/passenger")
public class PassengerController {

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping
    public String testMongo() {
        mongoTemplate.createCollection("test-collection");
        return "MongoDB connection successful!";
    }
}
