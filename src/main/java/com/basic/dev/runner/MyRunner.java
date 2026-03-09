package com.basic.dev.runner;

import com.basic.dev.config.CustomerVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Slf4j
@Component
public class MyRunner implements ApplicationRunner {

    @Value("${myboot.name}")
    private String name;

    @Autowired
    private CustomerVO customerVO;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("myboot.name = {}", name);

        log.debug("VM Argument foo : {}", args.containsOption("foo"));
        log.info("Program Argument bar : {}", args.containsOption("bar"));

        if (customerVO != null) {
            log.debug("customerVO = {}", customerVO);
            log.info("customerVO loaded successfully");
        } else {
            log.warn("customerVO is null");
        }

        // Anonymous Inner Class
        args.getOptionNames()
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        log.debug("option name = {}", s);
                    }
                });

        // Argument 목록 출력하기
        args.getOptionNames()
                .forEach(optionName -> log.info("argument option = {}", optionName));
    }
}