package com.basic.dev.runner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("vm arguement foo: "+ args.containsOption("foo"));
        System.out.println("program arguement bar: "+ args.containsOption("bar"));
    }
}