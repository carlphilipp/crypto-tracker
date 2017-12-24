package fr.cph.crypto.email

data class EmailProperties(val server: Server = Server(), val email: Email = Email()) {
    data class Server(var host: String? = null, var port: String? = null)
    data class Email(var username: String? = null, var password: String? = null, var from: String? = null)
}