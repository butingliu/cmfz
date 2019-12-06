package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
@Controller
@RequestMapping("safe")
public class SafeController {
    @RequestMapping("safeExit")
    public String safeExit(HttpSession session){
        session.invalidate();
        return "redirect:/back/login.jsp";
    }
}
