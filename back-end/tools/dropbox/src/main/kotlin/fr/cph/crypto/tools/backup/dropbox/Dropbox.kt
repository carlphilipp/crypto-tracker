package fr.cph.crypto.tools.backup.dropbox

import java.io.File

interface Dropbox {
	fun save(file: File)
}
