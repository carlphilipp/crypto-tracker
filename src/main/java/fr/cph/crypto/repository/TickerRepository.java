package fr.cph.crypto.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import fr.cph.crypto.domain.Ticker;

public interface TickerRepository extends MongoRepository<Ticker, String> {
}
