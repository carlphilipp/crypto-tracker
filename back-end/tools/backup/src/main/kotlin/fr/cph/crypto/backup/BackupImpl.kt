package fr.cph.crypto.backup

import fr.cph.crypto.backup.config.BackupConfig
import fr.cph.crypto.backup.config.BackupProperties
import fr.cph.crypto.tools.export.db.ExportDb
import fr.cph.crypto.tools.file.FileSystem
import fr.cph.crypto.tools.file.FileSystemImpl
import fr.cph.crypto.tools.export.db.mongo.MongoExport
import fr.cph.crypto.tools.backup.dropbox.Dropbox
import fr.cph.crypto.tools.backup.dropbox.DropboxImpl

class BackupImpl(
	private val config: BackupProperties = BackupConfig.backupProperties,
	private val fileSystem: FileSystem = FileSystemImpl(),
	private val mongoExport: ExportDb = MongoExport(config.tempDirectory!!, config.getArchiveName(), fileSystem),
	private val dropBoxClient: Dropbox = DropboxImpl()) : Backup {

	override fun backup() {
		// Export
		val archive = mongoExport.export()

		// Save to dropbox
		dropBoxClient.save(archive)

		// Clean temp directory
		fileSystem.cleanDirectory(config.tempDirectory!!)
	}
}
