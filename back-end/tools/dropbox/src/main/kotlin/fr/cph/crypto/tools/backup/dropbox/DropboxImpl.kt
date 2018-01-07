package fr.cph.crypto.tools.backup.dropbox

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import fr.cph.crypto.tools.backup.dropbox.config.DropboxConfig
import fr.cph.crypto.tools.backup.dropbox.config.DropboxProperties
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class DropboxImpl(
	private val dropboxProperties: DropboxProperties = DropboxConfig.properties,
	private val client: DbxClientV2 = DbxClientV2(DbxRequestConfig(dropboxProperties.clientId), dropboxProperties.accessToken)) : Dropbox {

	override fun save(file: File) {
		LOGGER.info("Saving to Dropbox: {}", file.absolutePath)
		uploadFile(file)

		// TODO
		//cleanOldFileIfNeeded(file)
	}

	private fun uploadFile(file: File) {
		try {
			FileInputStream(file.absolutePath).use({ `in` -> client.files().uploadBuilder("/" + file.name).uploadAndFinish(`in`) })
		} catch (ex: Exception) {
			LOGGER.error("Error while uploading DropBox file", ex)
		}
	}

	private fun cleanOldFileIfNeeded(path: String) {
		try {
			client.files().permanentlyDelete("/" + path)
		} catch (ex: Exception) {
			LOGGER.error("Error while deleting DropBox file", ex);
		}
	}

	internal fun calculateNewDateFromFileName(file: File): String {
		val date = file.name.substring(0, file.name.indexOf("-crypto"))
		val newLocalDate = LocalDate.parse(date, FORMATTER).minusDays(7)
		return newLocalDate.format(FORMATTER)
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(DropboxImpl::class.java)
		private val FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy")
	}
}
