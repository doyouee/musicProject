package com.doyouee.flo_service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doyouee.flo_service.entity.GenreEntity;

@Repository
public interface GenreRepository extends JpaRepository<GenreEntity, Long> {
    public Page<GenreEntity> findByGenreNameContains(String genreName, Pageable pageable);
    public Integer countByGenreName(String genreName);
}
