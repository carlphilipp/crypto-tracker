package fr.cph.crypto.tools.backup.db.mongo

import org.apache.commons.io.FileUtils
import java.io.File

interface FileSystem {
	fun listFiles(file: File): Array<File>

	fun deleteFile(file: File): Boolean

	fun deleteDirectory(file: File)
}

open class FileSystemImpl : FileSystem {

	override fun listFiles(file: File): Array<File> {
		return file.listFiles()
	}

	override fun deleteFile(file: File): Boolean {
		return file.delete()
	}

	override fun deleteDirectory(file: File) {
		FileUtils.deleteDirectory(file)
	}
}
