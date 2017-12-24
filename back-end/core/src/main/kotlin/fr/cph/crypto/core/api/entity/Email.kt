package fr.cph.crypto.core.api.entity

data class Email(val to: String,
                 val subject: String,
                 val content: String)