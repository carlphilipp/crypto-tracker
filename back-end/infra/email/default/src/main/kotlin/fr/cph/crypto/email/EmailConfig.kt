package fr.cph.crypto.email

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.lang.management.ManagementFactory

@Configuration
class EmailConfig {

    @Bean
    fun getProperties(): EmailProperties {
        val mapper = ObjectMapper(YAMLFactory())
        val emailProperties = mapper.readValue(File(EmailConfig::class.java.classLoader.getResource("email.yaml").path), EmailProperties::class.java)
        val decryptedPassword = decryptPassword(emailProperties.email.password!!)
        emailProperties.email.password = decryptedPassword
        return emailProperties
    }

    private fun decryptPassword(password: String): String {
        val stringEncryptor = StandardPBEStringEncryptor()
        val runtimeMxBean = ManagementFactory.getRuntimeMXBean()
        val argument = runtimeMxBean.inputArguments.find { s -> s.contains("jasypt.encryptor.password") } ?: throw RuntimeException("jasypt.encryptor.password not found")
        val jasyptPassword = argument.substring(argument.indexOf("=") + 1)
        stringEncryptor.setPassword(jasyptPassword)
        return stringEncryptor.decrypt(password)
    }
}