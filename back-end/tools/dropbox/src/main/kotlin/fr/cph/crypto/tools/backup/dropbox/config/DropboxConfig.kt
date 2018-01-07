package fr.cph.crypto.tools.backup.dropbox.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import java.lang.management.ManagementFactory

object DropboxConfig {

	val properties: DropboxProperties

	init {
		val mapper = ObjectMapper(YAMLFactory())
		properties = mapper.readValue(DropboxConfig::class.java.classLoader.getResourceAsStream("dropbox.yaml"), DropboxProperties::class.java)
		val decryptedPassword = decryptToken(properties.accessToken!!)
		properties.accessToken = decryptedPassword
	}

	private fun decryptToken(accessToken: String): String {
		val stringEncryptor = StandardPBEStringEncryptor()
		val runtimeMxBean = ManagementFactory.getRuntimeMXBean()
		val argument = runtimeMxBean.inputArguments.find { s -> s.contains("jasypt.encryptor.password") } ?: throw RuntimeException("jasypt.encryptor.password not found")
		val jasyptPassword = argument.substring(argument.indexOf("=") + 1)
		stringEncryptor.setPassword(jasyptPassword)
		return stringEncryptor.decrypt(accessToken)
	}
}

data class DropboxProperties(val clientId: String? = null, var accessToken: String? = null)
