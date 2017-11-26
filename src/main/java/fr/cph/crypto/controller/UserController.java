package fr.cph.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import fr.cph.crypto.domain.Position;
import fr.cph.crypto.domain.User;
import fr.cph.crypto.repository.UserRepository;
import fr.cph.crypto.service.UserService;

@RequestMapping(value = "/user")
@RestController
public class UserController {

	private final UserRepository repository;
	private final UserService userService;

	@Autowired
	public UserController(final UserRepository repository, UserService userService) {
		this.repository = repository;
		this.userService = userService;
	}

	@RequestMapping
	public List<User> getAll() {
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUser(@PathVariable("id") final String id) {
		return repository.findOne(id);
	}

	@RequestMapping(value = "/{id}/position/{positionId}", method = RequestMethod.PUT)
	public Position updatePosition(@PathVariable("id") final String id, @RequestBody final Position position) {
		return userService.updatePosition(position);
	}
}
