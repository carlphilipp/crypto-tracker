package fr.cph.crypto.rest.service

import fr.cph.crypto.core.spi.ContextService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class ContextServiceAdapter : ContextService {

    @Value("\${context.scheme}")
    private lateinit var scheme: String

    @Value("\${context.host}")
    private lateinit var host: String

    @Value("\${context.port}")
    private lateinit var port: String

    override fun getBaseUrl(): String {
        return if (scheme == "https") {
            "$scheme://$host"
        } else {
            "$scheme://$host:$port"
        }
    }
}