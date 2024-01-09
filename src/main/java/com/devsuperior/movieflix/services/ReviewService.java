package com.devsuperior.movieflix.services;

import java.util.Optional;
import java.util.Locale.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.MovieRepository;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private UserService userService;

    @Transactional
	public ReviewDTO insert(ReviewDTO dto) {
		Review entity = new Review();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new ReviewDTO(entity);
	}

	private void copyDtoToEntity(ReviewDTO dto, Review entity) {

        entity.setId(dto.getUserId());
        entity.setText(dto.getText());
		User user = userService.authenticated();

		Optional<Movie> movieResult = movieRepository.findById(dto.getMovieId());
		Movie movie = movieResult.orElseThrow(
            ()-> new ResourceNotFoundException("Recurso não encontrado"));

        entity.setUser(user);
		entity.setMovie(movie);
	}

}
