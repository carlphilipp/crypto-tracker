package fr.cph.crypto.gateway.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("backend")
class BackendProperties {
    var scheme: String? = null
    var host: String? = null
    var port: String? = null
    val security: Security = Security()

    class Security {
        val jwt: Jwt = Jwt()

        class Jwt {
            var clientId: String? = null
            var clientSecret: String? = null
        }
    }
}