package fr.cph.crypto.tools.file

import org.apache.commons.io.FileUtils
import java.io.File

interface FileSystem {

	fun cleanDirectory(directory: String)

	fun zipFile(outputFilePath: String, directory: String): File
}

open class FileSystemImpl(private val zipper: Zipper = ZipperImpl()) : FileSystem {

	override fun cleanDirectory(directory: String) {
		val folder = File(directory)
		folder.listFiles().forEach { file ->
			when {
				file.isDirectory -> FileUtils.deleteDirectory(file)
				file.isFile -> file.delete()
			}
		}
	}

	override fun zipFile(outputFilePath: String, directory: String): File {
		val lists = File(directory).listFiles()
		return zipper.zip(outputFilePath, lists.map { f -> f.absolutePath!! })
	}
}
