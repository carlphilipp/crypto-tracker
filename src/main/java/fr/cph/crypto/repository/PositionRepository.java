package fr.cph.crypto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.cph.crypto.domain.Position;

public interface PositionRepository extends MongoRepository<Position, Long> {
}
