package com.website.surotec_academy.service;

import com.website.surotec_academy.domain.dto.news.NewsCreatedDto;
import com.website.surotec_academy.domain.dto.news.NewsDto;
import com.website.surotec_academy.enums.NewsStatus;

import java.util.List;
import java.util.Optional;

public interface NewsService {
    // Crear una nueva noticia
    NewsCreatedDto createNews(NewsDto newsDto);

    // Buscar una noticia por su ID
    Optional<NewsDto> getNewsById(Integer id);

    // Buscar noticias por empleado
    List<NewsDto> getNewsByEmployeeId(Integer employeeId);

    // Buscar noticias por estado
    List<NewsDto> getNewsByStatus(NewsStatus status);

    // Actualizar una noticia existente
    Optional<NewsDto> updateNews(Integer id, NewsDto newsDto);

    // Eliminar una noticia
    void deleteNews(Integer id);
}
