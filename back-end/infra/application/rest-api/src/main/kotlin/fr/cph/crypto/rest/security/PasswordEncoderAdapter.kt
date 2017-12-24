package fr.cph.crypto.rest.security

import fr.cph.crypto.core.spi.PasswordEncoder
import org.springframework.security.authentication.encoding.ShaPasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordEncoderAdapter(private val shaPasswordEncoder: ShaPasswordEncoder) : PasswordEncoder {

    override fun encode(str: String): String {
        return shaPasswordEncoder.encodePassword(str, null)
    }
}