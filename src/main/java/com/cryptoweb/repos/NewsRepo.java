package com.cryptoweb.repos;

import com.cryptoweb.domain.News;
import com.cryptoweb.repos.cammon.BaseRepository;

import java.util.List;

public interface NewsRepo extends BaseRepository<News, Long> {

    List<News> findByTagNews(String tag);

}
