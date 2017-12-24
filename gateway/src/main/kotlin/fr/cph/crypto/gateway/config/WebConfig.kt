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
package fr.cph.crypto.gateway.config

import fr.cph.crypto.gateway.GatewayResponseErrorHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory
import org.springframework.http.client.support.BasicAuthorizationInterceptor
import org.springframework.web.client.RestTemplate

@Configuration
class WebConfig {

    @Autowired
    private lateinit var backendProperties: BackendProperties

    @Qualifier(value = "restTemplate")
    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactory())
        restTemplate.errorHandler = GatewayResponseErrorHandler()
        return restTemplate
    }

    @Qualifier(value = "restTemplateAuth")
    @Bean
    fun restTemplateAuth(): RestTemplate {
        val restTemplate = RestTemplate(HttpComponentsClientHttpRequestFactory())
        restTemplate.errorHandler = GatewayResponseErrorHandler()
        restTemplate.interceptors.add(BasicAuthorizationInterceptor(backendProperties.security.jwt.clientId, backendProperties.security.jwt.clientSecret))
        return restTemplate
    }
}