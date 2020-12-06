package com.jpa.kakao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ViewController {

    @GetMapping("/hello")
    public ModelAndView hello(){
        ModelAndView mdv = new ModelAndView();
        mdv.setViewName("/hello");
        return mdv;
    }

}
