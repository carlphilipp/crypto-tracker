package fr.cph.crypto.backend.scheduler

import fr.cph.crypto.backend.service.TickerService
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TickerScheduler(private val tickerService: TickerService) {

    @Scheduled(fixedRate = 300000, initialDelay = 5000)
    fun reportCurrentTime() {
        log.info("Update all tickers")
        tickerService.updateAll()
    }

    companion object {
        private val log = LoggerFactory.getLogger(TickerScheduler::class.java)
    }
}