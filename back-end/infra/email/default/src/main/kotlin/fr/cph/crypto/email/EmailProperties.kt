package fr.cph.crypto.email

data class EmailProperties(val smtp: Smtp = Smtp(), val from: From = From()) {
    data class Smtp(var host: String? = null, var port: String? = null)
    data class From(var username: String? = null, var password: String? = null, var from: String? = null)
}