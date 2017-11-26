package fr.cph.crypto.repository

import org.springframework.data.mongodb.repository.MongoRepository

import fr.cph.crypto.domain.User

interface UserRepository : MongoRepository<User, String>
