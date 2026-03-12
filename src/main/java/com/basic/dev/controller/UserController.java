package com.basic.dev.controller;

import com.basic.dev.entity.User;
import com.basic.dev.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 메인 페이지(전체 목록)
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    // 등록 페이지 열기
    @GetMapping("/signup")
    public String showSignUpForm(@ModelAttribute("user") User user) {
        return "add-user";
    }

    // 등록 처리
    @PostMapping("/adduser")
    public String addUser(@Valid @ModelAttribute("user") User user,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
        // return "redirect:/"; 로 바꿔도 됨
    }

    // 수정 페이지 열기
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 user id: " + id));

        model.addAttribute("user", user);
        return "update-user";
    }

    // 수정 처리
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("user") User user,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "update-user";
        }

        user.setId(id);
        userRepository.save(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
        // return "redirect:/"; 로 바꿔도 됨
    }

    // 삭제 처리
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 user id: " + id));

        userRepository.delete(user);
        model.addAttribute("users", userRepository.findAll());
        return "index";
        // return "redirect:/"; 로 바꿔도 됨
    }
}