package com.example.botsender.webapp.controllers;

import com.example.botsender.telegram.dto.User;
import com.example.botsender.webapp.utils.FileUtils;
import com.example.botsender.webapp.service.JsonService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;

@RestController
@RequestMapping("/main")
public class MainController {
    private final FileUtils utils;
    private final JsonService service;

    public MainController(FileUtils utils, JsonService service) {
        this.utils = utils;
        this.service = service;
    }

    @GetMapping
    public ModelAndView mainPage(Model model, User user) {
        model.addAttribute("user", user);
        return new ModelAndView("main_page");
    }

    @PostMapping
    public RedirectView saveUser(@RequestParam("file") MultipartFile file, @ModelAttribute("user") User user) throws IOException{
        utils.saveFile(file);
        service.objectToStringJson(file, user);
        return new RedirectView("/main");
    }
}


