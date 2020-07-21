package com.dong.freemarkerweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author LD
 * @date 2020/7/21 17:07
 */
@Controller
public class HelloController {

    @RequestMapping("/")
    public String index(){
        return "upload";
    }
}
