package com.cryptoweb.controller;

import com.cryptoweb.domain.News;
import com.cryptoweb.repos.NewsRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final NewsRepo newsRepo;

    @Value("${upload.path}")
    private String uploadPath;

    public MainController(NewsRepo newsRepo) {
        this.newsRepo = newsRepo;
    }

    @GetMapping("/")
    public String greeting(Model model) {
        Iterable<News> news = newsRepo.findAll();
        model.addAttribute("news", news);
        return "main";
    }


}