package fr.cph.crypto.maintenance.db.mongo

import fr.cph.crypto.maintenance.db.ExportDb
import org.slf4j.LoggerFactory
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class MongoExport(val mongoProperties: MongoProperties = MongoConfig.mongoProperties) : ExportDb {
	override fun export(): File {
		val dumpCommand = mongoProperties.mongo.mongodumppath + " --out " + mongoProperties.mongo.exportfolder + " --db " + mongoProperties.mongo.dbname
		Runtime.getRuntime().exec(dumpCommand)
		//LOGGER.info("File exported to '{}'", fileName)

		val file = File(mongoProperties.mongo.exportfolder + "/" + mongoProperties.mongo.dbname)

		val lists = file.listFiles()


		zip(lists.map { file -> file.absolutePath!! })

		return File("")
	}

	private fun zip(files: List<String>) {
		var out = ZipOutputStream(BufferedOutputStream(FileOutputStream("/home/carl/test/test.zip")))
		for (file in files) {
			var fi = FileInputStream(file)
			var origin = BufferedInputStream(fi)
			var entry = ZipEntry(file.substring(file.lastIndexOf("/")))
			out.putNextEntry(entry)
			origin.copyTo(out, 1024)
			origin.close()
		}
		out.close()
	}


	companion object {
		private val LOGGER = LoggerFactory.getLogger(MongoExport::class.java)
	}
}

fun main(args: Array<String>) {
	val mongoProperties = MongoProperties(MongoProperties.Mongo("/opt/mongodb/bin/mongodump", "/home/carl/test", "cryptodb"))
	val mongoExport = MongoExport(mongoProperties)
	mongoExport.export()
}


