package fr.cph.crypto.tools.export.db.mongo

import fr.cph.crypto.tools.export.db.ExportDb
import fr.cph.crypto.tools.file.FileSystem
import org.slf4j.LoggerFactory
import java.io.File

class MongoExport(
	private val exportFolder: String,
	private val outputFilePath: String,
	private val fileSystem: FileSystem,
	private val mongoProperties: MongoProperties = MongoConfig.mongoProperties,
	private val runtime: Runtime = Runtime.getRuntime()) : ExportDb {

	override fun export(): File {
		val dumpCommand = mongoProperties.mongo.mongodumppath + " --out " + exportFolder + " --db " + mongoProperties.mongo.dbname
		// TODO turn back to debug
		LOGGER.info("Running: {}", dumpCommand)
		val process = runtime.exec(dumpCommand)
		val exitVal = process.waitFor()
		if (exitVal == 0) {
			return fileSystem.zipFile(outputFilePath, exportFolder + "/" + mongoProperties.mongo.dbname)
		} else {
			throw RuntimeException()
		}
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(MongoExport::class.java)
	}
}
