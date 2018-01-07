/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.cph.crypto.backup.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import java.text.SimpleDateFormat
import java.util.*

object BackupConfig {

	val backupProperties: BackupProperties

	init {
		val mapper = ObjectMapper(YAMLFactory())
		backupProperties = mapper.readValue(BackupConfig::class.java.classLoader.getResourceAsStream("config.yaml"), BackupProperties::class.java)
	}
}

data class BackupProperties(val tempDirectory: String? = null, private val archiveName: String? = null) {

	fun getArchiveName(): String {
		return tempDirectory + "/" + getCurrentDateInFormat("MM-dd-yyyy") + "-" + archiveName + ".zip"
	}

	private fun getCurrentDateInFormat(format: String): String {
		val cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
		val formatter = SimpleDateFormat(format)
		return formatter.format(cal.time)
	}
}
