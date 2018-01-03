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
package fr.cph.crypto.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices

@Configuration
@EnableResourceServer
class ResourceServerConfig : ResourceServerConfigurerAdapter() {

	@Autowired
	private lateinit var tokenServices: ResourceServerTokenServices

	@Autowired
	private lateinit var jwtProperties: JwtProperties

	override fun configure(resources: ResourceServerSecurityConfigurer) {
		resources.resourceId(jwtProperties.resourceId).tokenServices(tokenServices)
	}

	override fun configure(http: HttpSecurity) {
		http
			.requestMatchers()
			.and()
			.authorizeRequests()
			.antMatchers("/actuator/**", "/api-docs/**").permitAll()
			.antMatchers("/api/**").authenticated()
	}
}
