package fr.cph.crypto.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import fr.cph.crypto.domain.Ticker;

public interface TickerRepository extends PagingAndSortingRepository<Ticker, String> {
}
