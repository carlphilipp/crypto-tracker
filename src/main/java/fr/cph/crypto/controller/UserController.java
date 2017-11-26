package fr.cph.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import fr.cph.crypto.domain.User;
import fr.cph.crypto.repository.UserRepository;

@RequestMapping(value = "/user")
@RestController
public class UserController {

	private final UserRepository repository;

	@Autowired
	public UserController(final UserRepository repository) {
		this.repository = repository;
	}

	@RequestMapping
	public List<User> getAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}")
	public User getUser(@PathVariable("id") final Long id) {
		return repository.findOne(id);
	}
}
