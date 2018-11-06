package com.cryptoweb.service;

import com.cryptoweb.domain.News;
import com.cryptoweb.domain.enums.CategoryEnamNews;
import com.cryptoweb.repos.NewsRepo;
import com.cryptoweb.repos.cammon.BaseRepository;
import com.cryptoweb.service.common.AbstractBaseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NewsService extends AbstractBaseService<News> {
    private final NewsRepo repo;

    public NewsService(NewsRepo repo) {
        this.repo = repo;
    }

    @Override
    public BaseRepository<News, Long> getRepository() {
        return repo;
    }

    @Override
    public List<News> getAll() {
        return repo.findAll();
    }

    public List<News> getByTag(String filter) {

        if (filter != null && !filter.isEmpty()) {
            return repo.findByTagNews(filter);
        } else {
            return repo.findAll();
        }
    }

    /*
    вытянуть все катигории в лис стрингов(для фронта)
     */
    public List<String> getAllCategory() {
        CategoryEnamNews[] mass = CategoryEnamNews.values();
        List<String> stringList = new ArrayList<>();
        for (CategoryEnamNews cat : mass) {
            stringList.add(cat.toString());
        }
        return stringList;
    }
}
