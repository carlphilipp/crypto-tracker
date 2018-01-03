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
package fr.cph.crypto.rest.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig : WebSecurityConfigurerAdapter() {

	@Value("\${security.signing-key}")
	private lateinit var signingKey: String

	@Autowired
	private lateinit var encoder: ShaPasswordEncoder

	@Autowired
	private lateinit var userService: UserDetailsService

	@Autowired
	private lateinit var jwtConverter: JwtConverter

	@Bean
	override fun authenticationManager(): AuthenticationManager {
		return super.authenticationManager()
	}

	override fun configure(auth: AuthenticationManagerBuilder) {
		auth.userDetailsService<UserDetailsService>(userService).passwordEncoder(encoder)
	}

	override fun configure(http: HttpSecurity) {
		http
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.httpBasic()
			.realmName("security realm")
			.and()
			.csrf()
			.disable()
	}

	@Bean
	fun accessTokenConverter(): JwtAccessTokenConverter {
		val converter = JwtAccessTokenConverter()
		converter.setSigningKey(signingKey)
		converter.accessTokenConverter = jwtConverter
		return converter
	}

	@Bean
	fun tokenStore(): TokenStore {
		return JwtTokenStore(accessTokenConverter())
	}

	@Bean
	@Primary
	fun tokenServices(): DefaultTokenServices {
		val defaultTokenServices = DefaultTokenServices()
		defaultTokenServices.setTokenStore(tokenStore())
		defaultTokenServices.setSupportRefreshToken(true)
		return defaultTokenServices
	}
}
