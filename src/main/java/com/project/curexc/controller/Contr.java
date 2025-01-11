package com.project.curexc.controller;

import com.project.curexc.service.Caller;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class Contr {
    private final Caller caller;

    @GetMapping("/call")
    public void call(){
        System.out.println(caller.call());
    }
}
