package com.website.surotec_academy.controller;


import com.website.surotec_academy.domain.dto.NewsCreatedDto;
import com.website.surotec_academy.domain.dto.NewsDto;
import com.website.surotec_academy.enums.NewsStatus;
import com.website.surotec_academy.service.NewsService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    //Crear noticia
    @PostMapping
    public ResponseEntity<NewsCreatedDto> createNews(@Valid @RequestBody NewsDto newsDto) {
        NewsCreatedDto created = newsService.createNews(newsDto);
        return ResponseEntity.ok(created);
    }

    //Obtener noticia por ID
    @GetMapping("/{id}")
    public ResponseEntity<NewsDto> getNewsById(@PathVariable Integer id) {
        return newsService.getNewsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Obtener noticias por ID de empleado
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<NewsDto>> getNewsByEmployeeId(@PathVariable Integer employeeId) {
        List<NewsDto> newsList = newsService.getNewsByEmployeeId(employeeId);
        return ResponseEntity.ok(newsList);
    }

    //Obtener noticias por estado (DRAFT, PUBLISHED, ARCHIVED)
    @GetMapping("/status/{status}")
    public ResponseEntity<List<NewsDto>> getNewsByStatus(@PathVariable NewsStatus status) {
        List<NewsDto> newsList = newsService.getNewsByStatus(status);
        return ResponseEntity.ok(newsList);
    }

    //Actualizar noticia
    @PutMapping("/{id}")
    public ResponseEntity<NewsDto> updateNews(
            @PathVariable Integer id,
            @Valid @RequestBody NewsDto newsDto) {

        return newsService.updateNews(id, newsDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Eliminar noticia
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNews(@PathVariable Integer id) {
        newsService.deleteNews(id);
        return ResponseEntity.noContent().build();
    }


}
