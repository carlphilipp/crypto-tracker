package fr.cph.crypto.config

import fr.cph.crypto.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter
import org.springframework.stereotype.Component

/**
 * Customize JWT payload
 */
@Component
internal class MyDefaultUserAuthenticationConverter : DefaultUserAuthenticationConverter() {

    @Autowired
    private val repository: UserRepository? = null

    override fun convertUserAuthentication(authentication: Authentication): Map<String, *> {
        val user = repository!!.findOneByEmail(authentication.name)
        val response = super.convertUserAuthentication(authentication) as MutableMap<String, Any>
        response.put(ID, user.id!!)
        return response
    }

    companion object {
        private val ID = "id"
    }
}
