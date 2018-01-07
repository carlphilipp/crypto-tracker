package fr.cph.crypto.maintenance.db

import java.io.File

interface ExportDb {
	fun export(): File

	fun cleanFileSystem()
}
