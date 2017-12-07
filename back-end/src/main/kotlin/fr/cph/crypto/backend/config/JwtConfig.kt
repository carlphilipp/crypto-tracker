package fr.cph.crypto.backend.config

import fr.cph.crypto.backend.repository.UserRepository
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter

@Configuration
class JwtConfig {

    @Bean
    fun defaultUserAuthenticationConverter(userRepository: UserRepository): DefaultUserAuthenticationConverter {
        return CustomUserAuthenticationConverter(userRepository)
    }

    @Bean
    fun jwtConverter(defaultUserAuthenticationConverter: DefaultUserAuthenticationConverter): JwtConverter {
        val jwtConverter = JwtConverter()
        jwtConverter.setUserTokenConverter(defaultUserAuthenticationConverter)
        return jwtConverter
    }
}

/**
 * Allow spring to copy JWT payload into Authentication object
 */
class JwtConverter : DefaultAccessTokenConverter(), JwtAccessTokenConverterConfigurer {

    override fun configure(converter: JwtAccessTokenConverter) {
        converter.accessTokenConverter = this
    }

    override fun extractAuthentication(map: Map<String, *>): OAuth2Authentication {
        val auth = super.extractAuthentication(map)
        auth.details = map
        return auth
    }
}

/**
 * Add user id into JWT payload
 */
private class CustomUserAuthenticationConverter(private val userRepository: UserRepository) : DefaultUserAuthenticationConverter() {

    @Suppress("UNCHECKED_CAST")
    override fun convertUserAuthentication(authentication: Authentication): Map<String, *> {
        val user = userRepository.findOneByEmail(authentication.name)
        val response = super.convertUserAuthentication(authentication) as MutableMap<String, Any>
        response.put(ID, user?.id!!)
        return response
    }

    companion object {
        private val ID = "id"
    }
}
