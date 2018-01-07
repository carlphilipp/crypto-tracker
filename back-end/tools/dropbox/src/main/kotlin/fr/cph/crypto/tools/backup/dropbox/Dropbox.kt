package fr.cph.crypto.tools.backup.dropbox

import java.io.File

interface Dropbox {
	fun uploadFile(file: File)
	fun deleteFile(path: String)
}
