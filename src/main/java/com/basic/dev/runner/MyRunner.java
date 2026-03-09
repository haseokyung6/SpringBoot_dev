package com.basic.dev.runner;

import com.basic.dev.config.CustomerVO;
import com.basic.dev.property.MyBootProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MyRunner implements ApplicationRunner {
    @Value("${myboot.name}")
    private String name;

    @Autowired
    private CustomerVO customerVO;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("${myboot.nam}  = " + name);

        System.out.println("VM Arguement foo : " + args.containsOption("foo"));
        System.out.println("Program Arguement bar : " + args.containsOption("bar"));

        //Anonymous Inner Class
        args.getOptionNames()
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println(s);
                    }
                });
        //Argument 목록 출력하기
        args.getOptionNames()//Set<String>
                .forEach(name -> System.out.println(name));
    }
}