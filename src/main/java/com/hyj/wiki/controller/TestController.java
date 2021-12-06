package com.hyj.wiki.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/6 13:27
 */

@RestController
public class TestController {

    @Value("${test.hello:test}")
    private String testHello;

    @GetMapping("/hello")
    public String hello(){
        return "hello world!" + testHello;
    }

    @PostMapping("/hello/post")
    public String helloPost(String name){
        return "hello world," + name;
    }
}
