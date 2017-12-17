package fr.cph.crypto.mongo

import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories

@EnableMongoRepositories(basePackages = ["fr.cph.crypto.mongo.repository"])
@Configuration
class Config