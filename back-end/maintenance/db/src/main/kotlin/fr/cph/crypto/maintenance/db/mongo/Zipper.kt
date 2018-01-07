package fr.cph.crypto.maintenance.db.mongo

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

interface Zipper {
	fun zip(path: String, files: List<String>): File;
}

open class ZipperImpl : Zipper {

	override fun zip(path: String, files: List<String>): File {
		val out = ZipOutputStream(BufferedOutputStream(FileOutputStream(path)))
		for (file in files) {
			val fi = FileInputStream(file)
			val origin = BufferedInputStream(fi)
			val entry = ZipEntry(file.substring(file.lastIndexOf("/")))
			out.putNextEntry(entry)
			origin.copyTo(out, 1024)
			origin.close()
		}
		out.close()
		return File(path)
	}
}
