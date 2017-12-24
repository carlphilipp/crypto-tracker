package fr.cph.crypto.core.spi

interface PasswordEncoder {
    fun encode(str: String): String
}