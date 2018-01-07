/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.cph.crypto.scheduler

import fr.cph.crypto.backup.Backup
import fr.cph.crypto.core.usecase.sharevalue.UpdateShareValue
import fr.cph.crypto.core.usecase.ticker.UpdateTicker
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class TickerScheduler(private val updateTicker: UpdateTicker) {

	@Scheduled(fixedRate = 300000, initialDelay = 5000)
	fun updateAllTickers() {
		LOGGER.info("Scheduler: Update all tickers")
		updateTicker.updateAll()
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(TickerScheduler::class.java)
	}
}

@Profile("prod")
@Component
class ShareValueSchedulerProd(private val updateShareValue: UpdateShareValue) {

	@Scheduled(cron = "0 0 0 * * *", zone = "GMT")
	fun updateAllUsersShareValue() {
		LOGGER.info("Scheduler: Refresh all users share value")
		updateShareValue.updateAllUsersShareValue()
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(ShareValueSchedulerProd::class.java)
	}
}

@Profile("dev")
@Component
class ShareValueSchedulerDev(private val updateShareValue: UpdateShareValue) {

	@Scheduled(cron = "0 0/30 * * * *", zone = "GMT")
	fun updateAllUsersShareValue() {
		LOGGER.info("Scheduler: Refresh all users share value")
		updateShareValue.updateAllUsersShareValue()
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(ShareValueSchedulerDev::class.java)
	}
}

@Profile("prod")
@Component
class BackupDB(private val backup: Backup) {

	@Scheduled(cron = "0 30 0 * * *", zone = "GMT")
	fun updateAllUsersShareValue() {
		LOGGER.info("Scheduler: Backup DB")
		backup.backup()
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(ShareValueSchedulerProd::class.java)
	}
}
