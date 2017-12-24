package fr.cph.crypto.template

import fr.cph.crypto.core.spi.TemplateService
import org.springframework.stereotype.Service

@Service
class DefaultTemplateAdapter : TemplateService {

    override fun welcomeContentEmail(baseUrl: String, userId: String, key: String): String {
        return "Hello,<br/><br/>" +
                "Please validate your account by clicking <a href='" + baseUrl + "/validate?key=" + key + "&userId=" + userId + "'>here</a><br/><br/>" +
                "The crypto tracker team"
    }
}