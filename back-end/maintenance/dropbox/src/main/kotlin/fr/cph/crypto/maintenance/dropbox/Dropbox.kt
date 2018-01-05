package fr.cph.crypto.maintenance.dropbox

import java.io.File

interface Dropbox {
	fun uploadFile(file: File)
	fun deleteFile(path: String)
}
