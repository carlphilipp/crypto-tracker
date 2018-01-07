package fr.cph.crypto.tools.backup.db

import java.io.File

interface ExportDb {
	fun export(): File

	fun cleanFileSystem()
}
