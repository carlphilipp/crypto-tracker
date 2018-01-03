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

        val emailProperties = mapper.readValue(EmailConfig::class.java.classLoader.getResourceAsStream("email.yaml"), EmailProperties::class.java)
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
