/**
 * Copyright 2017 Carl-Philipp Harmant
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
package fr.cph.crypto.rest.config

import fr.cph.crypto.core.spi.UserRepository
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
        val user = userRepository.findOneUserByEmail(authentication.name)
        val response = super.convertUserAuthentication(authentication) as MutableMap<String, Any>
        response.put(ID, user?.id!!)
        return response
    }

    companion object {
        private val ID = "id"
    }
}
