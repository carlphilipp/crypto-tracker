package fr.cph.crypto.config

import fr.cph.crypto.backup.Backup
import fr.cph.crypto.backup.BackupImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BackupConfig {
	@Bean
	fun backup(): Backup {
		return BackupImpl()
	}
}
