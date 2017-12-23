package fr.cph.crypto.email

import fr.cph.crypto.core.spi.EmailService
import org.springframework.stereotype.Service
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class EmailServiceAdapter(private val emailProperties: EmailProperties) : EmailService {

    private val sslFactory = "javax.net.ssl.SSLSocketFactory"

    override fun sendWelcomeEmail(email: String) {

        val debug = true

        val props = Properties()
        props.put("mail.smtp.host", emailProperties.smtp.host)
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.port", emailProperties.smtp.port)
        props.put("mail.smtp.socketFactory.port", emailProperties.smtp.port)
        props.put("mail.smtp.socketFactory.class", sslFactory)
        props.put("mail.smtp.socketFactory.fallback", "false")
        props.put("mail.smtp.ssl.enable", true)

        val session = Session.getDefaultInstance(props, object : Authenticator() {
            override fun getPasswordAuthentication(): PasswordAuthentication {
                return PasswordAuthentication(emailProperties.from.username, emailProperties.from.password)
            }
        })

        session.debug = debug

        val msg = MimeMessage(session)
        val addressFrom = InternetAddress(emailProperties.from.from)
        msg.setFrom(addressFrom)

        val addressTo = arrayOfNulls<InternetAddress>(1)
        addressTo[0] = InternetAddress(email)
        msg.setRecipients(Message.RecipientType.TO, addressTo)

        // Setting the Subject and Content Type
        msg.subject = "Welcome to crypto tracker"
        msg.setContent("Welcome to crypto tracker!", "text/plain")
        msg.sentDate = Date()
        Transport.send(msg)
    }
}