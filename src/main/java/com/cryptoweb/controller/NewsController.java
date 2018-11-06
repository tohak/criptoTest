package com.cryptoweb.controller;

import com.cryptoweb.utils.ControllerUtils;
import com.cryptoweb.domain.News;
import com.cryptoweb.domain.User;
import com.cryptoweb.domain.enums.CategoryEnamNews;
import com.cryptoweb.repos.NewsRepo;
import com.cryptoweb.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/main")
public class NewsController {
    @Value("${upload.path}")
    private String uploadPath;
    private final NewsRepo newsRepo;
    private final NewsService service;

    @Autowired
    public NewsController(NewsService service, NewsRepo newsRepo) {
        this.service = service;
        this.newsRepo = newsRepo;
    }

    @GetMapping
    public String newsList(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<News> news = service.getByTag(filter);
        List<String> categoryList = service.getAllCategory();
        model.addAttribute("news", news);
        model.addAttribute("filter", filter);
        model.addAttribute("categoryList", CategoryEnamNews.values());
        return "main";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String add(@AuthenticationPrincipal User user,
                      @Valid
                              News news,
                      @RequestParam("file") MultipartFile file,
                      BindingResult bindingResult,
                      Model model
    ) throws IOException {
        news.setAuthor(user);
        news.setCategoryEnum(CategoryEnamNews.TECHNICAL);
        news.setTime(LocalDateTime.now());
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("news", news);
        } else if (file != null && !file.getOriginalFilename().isEmpty()) {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));

            news.setFilename(resultFilename);

        }

        newsRepo.save(news);

        Iterable<News> allNews = newsRepo.findAll();

        model.addAttribute("news", allNews);

        return "main";
    }

    @GetMapping("{news}")
    public String viewNews(@PathVariable News news, Model model) {
        model.addAttribute("news", news);
        return "newss";
    }



}
