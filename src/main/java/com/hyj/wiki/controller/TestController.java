package com.hyj.wiki.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Chosen1
 * @date: 2021/12/6 13:27
 */

@RestController
public class TestController {

    @GetMapping("/hello")
    public String hello(){
        return "hello world";
    }
}
