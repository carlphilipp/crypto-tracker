package fr.cph.crypto.maintenance.db.mongo

import fr.cph.crypto.maintenance.db.ExportDb
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MongoExport(private val zipper: Zipper,
				  private val fileSystem: FileSystem,
				  private val mongoProperties: MongoProperties = MongoConfig.mongoProperties,
				  private val runtime: Runtime = Runtime.getRuntime()) : ExportDb {

	override fun export(): File {
		val dumpCommand = mongoProperties.mongo.mongodumppath + " --out " + mongoProperties.mongo.exportfolder + " --db " + mongoProperties.mongo.dbname
		val process = runtime.exec(dumpCommand)
		val exitVal = process.waitFor()
		if (exitVal == 0) {
			val file = File(mongoProperties.mongo.exportfolder + "/" + mongoProperties.mongo.dbname)
			val outputFilePath = mongoProperties.mongo.exportfolder + "/" + getCurrentDateInFormat("MM-dd-yyyy") + "-" + mongoProperties.mongo.dbname + ".zip"
			val lists = fileSystem.listFiles(file)
			return zipper.zip(outputFilePath, lists.map { f -> f.absolutePath!! })
		} else {
			throw RuntimeException()
		}
	}

	override fun cleanFileSystem() {
		val cryptoDb = File(mongoProperties.mongo.exportfolder + "/" + mongoProperties.mongo.dbname)
		fileSystem.deleteDirectory(cryptoDb)
		val outputFile = File(mongoProperties.mongo.exportfolder + "/" + getCurrentDateInFormat("MM-dd-yyyy") + "-" + mongoProperties.mongo.dbname + ".zip")
		val deleted = fileSystem.deleteFile(outputFile)
		if (!deleted) {
			throw RuntimeException()
		}
	}

	private fun getCurrentDateInFormat(format: String): String {
		val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
		val formatter = SimpleDateFormat(format)
		return formatter.format(cal.time)
	}
}
