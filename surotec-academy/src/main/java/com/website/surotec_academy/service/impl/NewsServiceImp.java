package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.news.NewsCreatedDto;
import com.website.surotec_academy.domain.dto.news.NewsDto;
import com.website.surotec_academy.domain.mapper.NewsMapper;
import com.website.surotec_academy.entity.NewsEntity;
import com.website.surotec_academy.enums.NewsStatus;
import com.website.surotec_academy.repository.NewsRepository;
import com.website.surotec_academy.service.NewsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class NewsServiceImp implements NewsService {

    private final NewsRepository newsRepository;

    // Constructor para inyección de dependencias
    public NewsServiceImp(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Override
    public NewsCreatedDto createNews(NewsDto newsDto) {
        NewsEntity entity = NewsMapper.toEntity(newsDto);
//        entity.setPublishDate(LocalDateTime.now());
        NewsEntity saved = newsRepository.save(entity);
        return NewsMapper.toCreatedDto(saved);
    }


    @Override
    public Optional<NewsDto> getNewsById(Integer id) {
        return newsRepository.findById(id).map(NewsMapper::toDto);
    }

    @Override
    public List<NewsDto> getNewsByEmployeeId(Integer employeeId) {
        return newsRepository.findByEmployeeId(employeeId)
                .stream()
                .map(NewsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<NewsDto> getNewsByStatus(NewsStatus status) {
        return newsRepository.findByStatus(status)
                .stream()
                .map(NewsMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NewsDto> updateNews(Integer id, NewsDto newsDto) {
        return newsRepository.findById(id).map(existing -> {
            existing.setTitle(newsDto.title());
            existing.setContent(newsDto.content());
            existing.setStatus(newsDto.status());
            existing.setPublishDate(newsDto.publishDate());
            NewsEntity updated = newsRepository.save(existing);
            return NewsMapper.toDto(updated);
        });
    }

    @Override
    public void deleteNews(Integer id) {
        newsRepository.deleteById(id);
    }

}
