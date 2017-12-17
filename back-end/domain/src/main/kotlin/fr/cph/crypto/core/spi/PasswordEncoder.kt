package fr.cph.crypto.core.spi

interface PasswordEncoder {
    fun encodePassword(password: String): String
}