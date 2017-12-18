package fr.cph.crypto.rest.security

import fr.cph.crypto.core.spi.PasswordEncoder
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.stereotype.Component

@Component
class PasswordEncoderAdapter(private val shaPasswordEncoder: ShaPasswordEncoder) : PasswordEncoder {

    override fun encodePassword(password: String): String {
        return shaPasswordEncoder.encodePassword(password, null)
    }
}