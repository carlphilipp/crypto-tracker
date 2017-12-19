package fr.cph.crypto.rest.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("security.jwt")
class JwtProperties {
    var clientId: String? = null
    var clientSecret: String? = null
    var resourceId: String? = null
    var grantTypes: Array<String> = emptyArray()
    var scopes: Array<String> = emptyArray()
}