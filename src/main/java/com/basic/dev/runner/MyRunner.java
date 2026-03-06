package com.basic.dev.runner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyRunner implements ApplicationRunner {
    @Value("${myboot.name}")
    private String name;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("${myboot.nam}  = " + name);

        System.out.println("VM Arguement foo : " + args.containsOption("foo"));
        System.out.println("Program Arguement bar : " + args.containsOption("bar"));

        //Argument 목록 출력하기
        args.getOptionNames()//Set<String>
                .forEach(name -> System.out.println(name));
    }
}