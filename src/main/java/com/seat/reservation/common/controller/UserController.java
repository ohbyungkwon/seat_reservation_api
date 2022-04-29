package com.seat.reservation.common.controller;

import com.seat.reservation.common.domain.User;
import com.seat.reservation.common.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

/* 회원 등록, 수정, 탈퇴 (조회 기능은 Spring Security에서 자동으로 함) */
@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Inject
    public UserController(UserService userService){
        this.userService = userService;
    }

    @RequestMapping(value="/register",method = RequestMethod.GET)
    public String registerGet() throws Exception {
        return "/user/register";
    }

    @RequestMapping(value="/register", method = RequestMethod.POST)
    public String registerPost(User user) throws Exception {
        userService.registerUser(user);
        return "redirect:/user/login";
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String login() throws Exception {
        return "/user/loginForm";
    }
}
