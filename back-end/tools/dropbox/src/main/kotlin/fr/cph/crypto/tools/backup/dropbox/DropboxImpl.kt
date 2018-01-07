package fr.cph.crypto.tools.backup.dropbox

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream

class DropboxImpl : Dropbox {

	val config = DbxRequestConfig("clientId")
	val client = DbxClientV2(config, "token")

	override fun save(file: File) {
		uploadFile(file)

	}

	private fun uploadFile(file: File) {
		try {
			FileInputStream(file.name).use({ `in` -> client.files().uploadBuilder("/" + file.name).uploadAndFinish(`in`) })
		} catch (ex: Exception) {
			LOGGER.error("Error while uploading DropBox file", ex);
		}
	}

	private fun deleteFile(path: String) {
		try {
			client.files().permanentlyDelete("/" + path)
		} catch (ex: Exception) {
			LOGGER.error("Error while deleting DropBox file", ex);
		}
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(DropboxImpl::class.java)
	}
}
