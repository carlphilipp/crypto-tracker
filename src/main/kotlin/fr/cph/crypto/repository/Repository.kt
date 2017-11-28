package fr.cph.crypto.repository

import fr.cph.crypto.domain.Position
import fr.cph.crypto.domain.Ticker
import fr.cph.crypto.domain.User
import org.springframework.data.mongodb.repository.MongoRepository

interface PositionRepository : MongoRepository<Position, Long>

interface TickerRepository : MongoRepository<Ticker, String>

interface UserRepository : MongoRepository<User, String>
