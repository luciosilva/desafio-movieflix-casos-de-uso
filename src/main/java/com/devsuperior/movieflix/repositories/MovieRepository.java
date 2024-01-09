package com.devsuperior.movieflix.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.entities.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query(nativeQuery = true, value = """
            SELECT DISTINCT *
            FROM tb_movie
            WHERE (:genreId IS NULL OR tb_movie.genre_id = :genreId)
            """)
    Page<Movie> searchMovieByGenre(Pageable pageRequest, Long genreId);


}