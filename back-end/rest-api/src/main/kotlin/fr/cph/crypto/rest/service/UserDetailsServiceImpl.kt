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
package fr.cph.crypto.rest.service

import fr.cph.crypto.core.usecase.user.LoginUser
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl
constructor(private val loginUser: LoginUser) : UserDetailsService {

	override fun loadUserByUsername(username: String): UserDetails {
		val user = loginUser.login(username)
		val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority(user.role.name))
		return org.springframework.security.core.userdetails.User(user.email, user.password, authorities)
	}
}


