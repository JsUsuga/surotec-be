package com.website.surotec_academy.service.impl;

import com.website.surotec_academy.domain.dto.news.NewsCreatedDto;
import com.website.surotec_academy.domain.dto.news.NewsDto;
import com.website.surotec_academy.entity.NewsEntity;
import com.website.surotec_academy.enums.NewsStatus;
import com.website.surotec_academy.repository.NewsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsServiceImpTest {

    @Mock
    private NewsRepository newsRepository;

    @InjectMocks
    private NewsServiceImp newsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNews() {
        // Arrange
        NewsDto dto = NewsDto.builder()
                .employeeId(1)
                .title("Noticia de prueba")
                .content("Contenido de la noticia")
                .publishDate(LocalDateTime.now())
                .urlImage("https://example.com/img.png")
                .status(NewsStatus.PUBLISHED)
                .build();

        NewsEntity savedEntity = new NewsEntity();
        savedEntity.setId(1);
        savedEntity.setEmployeeId(dto.employeeId());
        savedEntity.setTitle(dto.title());
        savedEntity.setContent(dto.content());
        savedEntity.setImagesUrl(dto.urlImage());
        savedEntity.setStatus(dto.status());
        savedEntity.setPublishDate(dto.publishDate());

        when(newsRepository.save(any(NewsEntity.class))).thenReturn(savedEntity);

        // Act
        NewsCreatedDto created = newsService.createNews(dto);

        // Assert
        assertNotNull(created);
        assertEquals(1, created.idNews()); // Solo tiene idNews
        verify(newsRepository, times(1)).save(any(NewsEntity.class));
    }

    @Test
    void getNewsById() {
        NewsEntity entity = new NewsEntity();
        entity.setId(1);
        entity.setTitle("Noticia encontrada");

        when(newsRepository.findById(1)).thenReturn(Optional.of(entity));

        var result = newsService.getNewsById(1);

        assertTrue(result.isPresent());
        assertEquals("Noticia encontrada", result.get().title());
        verify(newsRepository).findById(1);
    }

    @Test
    void deleteNews() {
        doNothing().when(newsRepository).deleteById(1);

        newsService.deleteNews(1);

        verify(newsRepository, times(1)).deleteById(1);
    }
}
