package fr.cph.crypto.tools.export.db

import java.io.File

interface ExportDb {
	fun export(): File
}
