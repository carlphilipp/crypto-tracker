package fr.cph.crypto.email

import fr.cph.crypto.core.spi.EmailService
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailServiceAdapter(private val javaMailSender: JavaMailSender) : EmailService {

    override fun sendWelcomeEmail(email: String) {
        // TODO to implement
        val message = SimpleMailMessage()
        message.setTo(email)
        message.subject = "welcome"
        message.text = "cool story bro"
        //javaMailSender.send(message)
    }
}