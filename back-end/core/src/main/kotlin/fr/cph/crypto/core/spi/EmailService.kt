package fr.cph.crypto.core.spi

interface EmailService {

    fun sendWelcomeEmail(email: String)
}