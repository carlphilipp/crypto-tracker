package fr.cph.crypto.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import fr.cph.crypto.domain.Ticker;
import fr.cph.crypto.domain.TickerId;

public interface TickerRepository extends PagingAndSortingRepository<Ticker, TickerId> {
}
