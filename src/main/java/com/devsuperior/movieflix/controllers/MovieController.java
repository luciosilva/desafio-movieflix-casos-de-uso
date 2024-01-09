package com.devsuperior.movieflix.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.dto.MovieCardDTO;
import com.devsuperior.movieflix.dto.MovieDetailsDTO;
import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.services.GenreService;
import com.devsuperior.movieflix.services.MovieService;
import com.devsuperior.movieflix.services.UserService;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

import java.security.Principal;
import java.util.List;
import org.springframework.data.domain.Sort.Direction;

@RestController
@RequestMapping(value = "/movies")
public class MovieController {

	@Autowired
	private MovieService service;

    @PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @GetMapping(value = "/{id}")
	public ResponseEntity<MovieDetailsDTO> findById(@PathVariable Long id) {
		MovieDetailsDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PreAuthorize("hasAnyRole('VISITOR', 'MEMBER')")
    @RequestMapping
    public ResponseEntity<Page<MovieCardDTO>> findAll(@SortDefault(sort = "title", direction = Sort.Direction.ASC) Pageable pageable,
				@RequestParam(value = "genreId", defaultValue = "0") String genreId
			) {
        Page<MovieCardDTO> list = service.findSearch(pageable,genreId);
        return ResponseEntity.ok().body(list);
    }

}
