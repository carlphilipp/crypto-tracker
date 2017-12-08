package fr.cph.crypto.backend.scheduler

import fr.cph.crypto.backend.service.TickerService
import fr.cph.crypto.backend.service.UserService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class Scheduler(
        private val tickerService: TickerService,
        private val userService: UserService) {

    @Scheduled(fixedRate = 300000, initialDelay = 5000)
    fun updateAllTickers() {
        log.info("Update all tickers")
        tickerService.updateAll()
    }

    //@Scheduled(cron = "0 0 0 * * *", zone = "GMT")
    @Scheduled(cron = "0 0/30 * * * *", zone = "GMT")
    fun updateAllUsersShareValue() {
        log.info("Refresh all users share value")
        userService.updateAllUsersShareValue()
    }

    companion object {
        private val log = LoggerFactory.getLogger(Scheduler::class.java)
    }
}