package ru.otus.hw13.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    public String viewIndexPage() {
        return "index.html";
    }

}
