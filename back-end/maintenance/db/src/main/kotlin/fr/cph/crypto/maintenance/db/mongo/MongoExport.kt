package fr.cph.crypto.maintenance.db.mongo

import fr.cph.crypto.maintenance.db.ExportDb
import org.slf4j.LoggerFactory
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class MongoExport(private val mongoProperties: MongoProperties = MongoConfig.mongoProperties) : ExportDb {
	override fun export(): File {
		val dumpCommand = mongoProperties.mongo.mongodumppath + " --out " + mongoProperties.mongo.exportfolder + " --db " + mongoProperties.mongo.dbname
		Runtime.getRuntime().exec(dumpCommand)
		//LOGGER.info("File exported to '{}'", fileName)

		return File("")
	}

	companion object {
		private val LOGGER = LoggerFactory.getLogger(MongoExport::class.java)
	}
}

/*fun main(args: Array<String>) {
	val mongoExport = MongoExport()
	mongoExport.export()
}*/

fun main(args: Array<String>) {
	var files: Array<String> = arrayOf("/home/matte/theres_no_place.png", "/home/matte/vladstudio_the_moon_and_the_ocean_1920x1440_signed.jpg")
	var out = ZipOutputStream(BufferedOutputStream(FileOutputStream("/home/matte/Desktop/test.zip")))
	var data = ByteArray(1024)
	for (file in files) {
		var fi = FileInputStream(file)
		var origin = BufferedInputStream(fi)
		var entry = ZipEntry(file.substring(file.lastIndexOf("/")))
		out.putNextEntry(entry)
		origin.buffered(1024).reader().forEachLine {
			out.write(data)
		}
		origin.close()
	}
	out.close()
}
