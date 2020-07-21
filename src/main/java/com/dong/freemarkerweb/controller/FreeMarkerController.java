package com.dong.freemarkerweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

/**
 * @author LD
 */
@Controller
@RequestMapping("/freeMarker")
public class FreeMarkerController {

    @RequestMapping("/")
    public String index(Model model){
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            map.put("key_" + i, "value_" + i);
        }
        model.addAttribute("list", Arrays.asList("string1", "string2", "string3", "string4", "string5", "string6"));
        model.addAttribute("map", map);
        model.addAttribute("name", "   htTps://wWw.zHyD.mE   ");
        model.addAttribute("htmlText", "<span style=\"color: red;font-size: 16px;\">html内容</span>");
        model.addAttribute("num", 123.012);
        model.addAttribute("null", null);
        model.addAttribute("dateObj", new Date());
        model.addAttribute("bol", true);
        return "index";
    }


}
