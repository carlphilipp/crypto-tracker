package fr.cph.crypto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.cph.crypto.domain.User;

public interface UserRepository extends MongoRepository<User, String> {
}
