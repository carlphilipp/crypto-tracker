package fr.cph.crypto.backend.scheduler

import fr.cph.crypto.core.api.TickerService
import fr.cph.crypto.core.api.UserService
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TickerScheduler(private val tickerService: TickerService) {

    @Scheduled(fixedRate = 300000, initialDelay = 5000)
    fun updateAllTickers() {
        LOGGER.info("Update all tickers")
        tickerService.updateAll()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(TickerScheduler::class.java)
    }
}

@Profile("prod")
@Component
class ShareValueSchedulerProd(private val userService: UserService) {

    @Scheduled(cron = "0 0 0 * * *", zone = "GMT")
    fun updateAllUsersShareValue() {
        LOGGER.info("Refresh all users share value")
        userService.updateAllUsersShareValue()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ShareValueSchedulerProd::class.java)
    }
}

@Profile("dev")
@Component
class ShareValueSchedulerDev(private val userService: UserService) {

    @Scheduled(cron = "0 0/30 * * * *", zone = "GMT")
    fun updateAllUsersShareValue() {
        LOGGER.info("Refresh all users share value")
        userService.updateAllUsersShareValue()
    }

    companion object {

        private val LOGGER = LoggerFactory.getLogger(ShareValueSchedulerDev::class.java)
    }
}