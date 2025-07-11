package com.david.restapi.controller;


import com.david.restapi.models.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {




    AtomicLong counter = new AtomicLong();


    @GetMapping("/greeting")
    public Greeting greetings(@RequestParam(value = "name") String name){
        Greeting greeting = new Greeting();
        greeting.setId(counter.incrementAndGet());
        greeting.setContent("Hey Hii "+name);
        return greeting;
    }


}
