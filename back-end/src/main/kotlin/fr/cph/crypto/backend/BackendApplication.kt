package fr.cph.crypto.backend

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@EnableMongoRepositories(basePackages = ["fr.cph.crypto.backend.repository"]) // temp solution time to move repo into right subproject
@ComponentScan(value = ["fr.cph.crypto"])
@EnableScheduling
@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
    SpringApplication.run(BackendApplication::class.java, *args)
}
