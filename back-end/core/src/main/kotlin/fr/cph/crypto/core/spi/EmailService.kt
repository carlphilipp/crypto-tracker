package fr.cph.crypto.core.spi

import fr.cph.crypto.core.api.entity.Email

interface EmailService {

    fun sendWelcomeEmail(email: Email)
}