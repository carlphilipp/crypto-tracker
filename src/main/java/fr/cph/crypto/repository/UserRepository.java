package fr.cph.crypto.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import fr.cph.crypto.domain.User;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
}
