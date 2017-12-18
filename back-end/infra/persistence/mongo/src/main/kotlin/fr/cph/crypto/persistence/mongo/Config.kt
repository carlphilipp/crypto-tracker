package fr.cph.crypto.persistence.mongo

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories(basePackages = ["fr.cph.crypto.persistence.mongo.repository"])
@Configuration
class Config