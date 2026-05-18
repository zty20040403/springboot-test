package com.zhu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class hellocontroller {
    @RequestMapping
    public String sayHello() {
        return "Hello World----------";

    }
}
