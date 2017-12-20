package fr.cph.crypto.rest.service

import fr.cph.crypto.core.spi.UserRepository
import fr.cph.crypto.rest.exception.NotAllowedException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl
constructor(private val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findOneUserByEmail(username) ?: throw NotAllowedException()
        val authorities = listOf<GrantedAuthority>(SimpleGrantedAuthority(user.role.name))
        return org.springframework.security.core.userdetails.User(user.email, user.password, authorities)
    }
}


