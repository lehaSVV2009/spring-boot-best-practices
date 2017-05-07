package com.kadet.best.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NavController {

  @GetMapping("/categories/{category}")
  public String menu(@PathVariable(required = false) String category) {
    return "menu";
  }

}
