package fr.cph.crypto.controller

import fr.cph.crypto.domain.User
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class CustomPermissionEvaluator : PermissionEvaluator {

    @Value("\${security.jwt.client-id}")
    private lateinit var clientId: String

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any): Boolean {
        if (authentication == null || targetDomainObject == null || permission !is String) {
            return false
        }

        if (authentication.name == clientId) {
            return true
        }

        val result = if (targetDomainObject is User) {
            hasPrivilege(authentication, targetDomainObject)
        } else {
            hasPrivilege(authentication, targetDomainObject.javaClass.simpleName.toUpperCase(), permission.toString().toUpperCase())
        }
        if (!result) {
            LOGGER.warn("Denying user " + authentication.name + " permission '" + permission + "' on object " + targetDomainObject)
        }
        return result
    }

    override fun hasPermission(authentication: Authentication?, targetId: Serializable, targetType: String?, permission: Any): Boolean {
        return if (authentication == null || targetType == null || permission !is String) {
            false
        } else hasPrivilege(authentication, targetType.toUpperCase(), permission.toString().toUpperCase())
    }

    private fun hasPrivilege(auth: Authentication, targetType: String, permission: String): Boolean {
        return auth.authorities.any { it.authority.startsWith(targetType) && it.authority.contains(permission) }
    }

    private fun hasPrivilege(auth: Authentication, user: User): Boolean {
        return auth.name == user.email
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(CustomPermissionEvaluator::class.java)
    }
}
