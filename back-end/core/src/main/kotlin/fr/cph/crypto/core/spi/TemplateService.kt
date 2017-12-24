package fr.cph.crypto.core.spi

interface TemplateService {

    fun welcomeContentEmail(baseUrl: String, userId: String, key: String): String
}