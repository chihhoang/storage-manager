package com.storage.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomeController {
  @GetMapping("/healthCheck")
  public String healthCheck() {
    return "Health Check Okay";
  }
}
