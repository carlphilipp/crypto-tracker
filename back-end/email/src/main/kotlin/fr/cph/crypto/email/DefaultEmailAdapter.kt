/**
 * Copyright 2018 Carl-Philipp Harmant
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.cph.crypto.email

import fr.cph.crypto.core.entity.Email
import fr.cph.crypto.core.spi.EmailService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

@Service
class DefaultEmailAdapter(private val emailProperties: EmailProperties) : EmailService {

    private val sslFactory = "javax.net.ssl.SSLSocketFactory"

    override fun sendWelcomeEmail(email: Email) {
        Thread({
            val props = Properties()
            props.put("mail.smtp.host", emailProperties.server.host)
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", emailProperties.server.port)
            props.put("mail.smtp.socketFactory.port", emailProperties.server.port)
            props.put("mail.smtp.socketFactory.class", sslFactory)
            props.put("mail.smtp.socketFactory.fallback", "false")
            props.put("mail.smtp.ssl.enable", true)

            val session = Session.getDefaultInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(emailProperties.email.username, emailProperties.email.password)
                }
            })

            session.debug = false

            val msg = MimeMessage(session)
            val addressFrom = InternetAddress(emailProperties.email.from)
            msg.setFrom(addressFrom)

            val addressTo = arrayOfNulls<InternetAddress>(1)
            addressTo[0] = InternetAddress(email.to)
            msg.setRecipients(Message.RecipientType.TO, addressTo)

            // Setting the Subject and Content Type
            msg.subject = email.subject
            msg.setContent(email.content, "text/html")
            msg.sentDate = Date()
            LOGGER.debug("Sending email to [{}]", email.to)
            Transport.send(msg)
        }).start()
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(DefaultEmailAdapter::class.java)
    }
}
