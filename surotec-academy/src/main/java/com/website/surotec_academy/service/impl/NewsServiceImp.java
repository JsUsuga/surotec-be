package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.news.NewsCreatedDto;
import com.website.surotec_academy.domain.dto.news.NewsDto;
import com.website.surotec_academy.domain.mapper.NewsMapper;
import com.website.surotec_academy.entity.NewsEntity;
import com.website.surotec_academy.enums.NewsStatus;
import com.website.surotec_academy.repository.NewsRepository;
import com.website.surotec_academy.service.NewsService;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NewsServiceImp implements NewsService {

    private static final Logger log = LoggerFactory.getLogger(NewsServiceImp.class);

    private final NewsRepository newsRepository;

    // Constructor para inyección de dependencias
    public NewsServiceImp(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
        log.info("NewsServiceImp initialized with NewsRepository");
    }

    @Override
    public NewsCreatedDto createNews(NewsDto newsDto) {
        log.info("Creating news with title: '{}', authorId: {}", newsDto.title(), newsDto.employeeId());
        try {
            NewsEntity entity = NewsMapper.toEntity(newsDto);
            //        entity.setPublishDate(LocalDateTime.now());
            NewsEntity saved = newsRepository.save(entity);
            log.info("News created successfully with id: {}", saved.getId());
            return NewsMapper.toCreatedDto(saved);
        } catch (Exception e) {
            log.error("Error creating news with title: '{}'", newsDto.title(), e);
            throw new RuntimeException("Error creating news: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<NewsDto> getNewsById(Integer id) {
        log.info("Fetching news with id: {}", id);
        try {
            Optional<NewsDto> result = newsRepository.findById(id).map(NewsMapper::toDto);
            if (result.isEmpty()) {
                log.warn("No news found with id: {}", id);
            } else {
                log.info("News found with id: {}", id);
            }
            return result;
        } catch (Exception e) {
            log.error("Error fetching news with id: {}", id, e);
            throw new RuntimeException("Error fetching news: " + e.getMessage(), e);
        }
    }

    @Override
    public List<NewsDto> getNewsByEmployeeId(Integer employeeId) {
        log.info("Fetching news created by employeeId: {}", employeeId);
        try {
            List<NewsDto> result = newsRepository.findByEmployeeId(employeeId)
                    .stream()
                    .map(NewsMapper::toDto)
                    .collect(Collectors.toList());
            log.info("Found {} news entries for employeeId: {}", result.size(), employeeId);
            return result;
        } catch (Exception e) {
            log.error("Error fetching news by employeeId: {}", employeeId, e);
            throw new RuntimeException("Error fetching news by employeeId: " + e.getMessage(), e);
        }
    }

    @Override
    public List<NewsDto> getNewsByStatus(NewsStatus status) {
        log.info("Fetching news with status: {}", status);
        try {
            List<NewsDto> result = newsRepository.findByStatus(status)
                    .stream()
                    .map(NewsMapper::toDto)
                    .collect(Collectors.toList());
            log.info("Found {} news entries with status: {}", result.size(), status);
            return result;
        } catch (Exception e) {
            log.error("Error fetching news by status: {}", status, e);
            throw new RuntimeException("Error fetching news by status: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<NewsDto> updateNews(Integer id, NewsDto newsDto) {
        log.info("Updating news with id: {}, title: '{}', new status: {}", id, newsDto.title(), newsDto.status());
        try {
            Optional<NewsDto> updated = newsRepository.findById(id).map(existing -> {
                existing.setTitle(newsDto.title());
                existing.setContent(newsDto.content());
                existing.setStatus(newsDto.status());
                existing.setPublishDate(newsDto.publishDate());
                NewsEntity saved = newsRepository.save(existing);
                log.info("News updated successfully with id: {}", id);
                return NewsMapper.toDto(saved);
            });
            if (updated.isEmpty()) {
                log.warn("No news found to update with id: {}", id);
            }
            return updated;
        } catch (Exception e) {
            log.error("Error updating news with id: {}", id, e);
            throw new RuntimeException("Error updating news: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteNews(Integer id) {
        log.info("Deleting news with id: {}", id);
        try {
            newsRepository.deleteById(id);
            log.info("News deleted successfully with id: {}", id);
        } catch (Exception e) {
            log.error("Error deleting news with id: {}", id, e);
            throw new RuntimeException("Error deleting news: " + e.getMessage(), e);
        }
    }

}
