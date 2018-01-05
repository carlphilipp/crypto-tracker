package fr.cph.crypto.maintenance.dropbox

import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.v2.DbxClientV2
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream

class DropboxImpl : Dropbox {

	override fun uploadFile(file: File) {
		try {
			FileInputStream(file.name).use({ `in` -> client.files().uploadBuilder("/" + file.name).uploadAndFinish(`in`) })
		} catch (ex: Exception) {
			LOGGER.error("Error while uploading DropBox file", ex);
		}
	}

	override fun deleteFile(path: String) {
		try {
			client.files().permanentlyDelete("/" + path)
		} catch (ex: Exception) {
			LOGGER.error("Error while deleting DropBox file", ex);
		}
	}

	val config = DbxRequestConfig("clientId")
	val client = DbxClientV2(config, "token")

	companion object {
		private val LOGGER = LoggerFactory.getLogger(DropboxImpl::class.java)
	}
}
