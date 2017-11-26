package fr.cph.crypto.repository

import org.springframework.data.mongodb.repository.MongoRepository

import fr.cph.crypto.domain.Ticker

interface TickerRepository : MongoRepository<Ticker, String>
