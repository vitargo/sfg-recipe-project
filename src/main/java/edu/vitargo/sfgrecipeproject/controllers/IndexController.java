package edu.vitargo.sfgrecipeproject.controllers;

import org.springframework.web.bind.annotation.RequestMapping;

public class IndexController {

    @RequestMapping({"", "/", "/index"})
    String getIndex(){
        return "index";
    }
}
