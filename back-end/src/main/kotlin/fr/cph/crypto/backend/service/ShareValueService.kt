package fr.cph.crypto.backend.service

import fr.cph.crypto.backend.domain.ShareValue
import fr.cph.crypto.backend.domain.User

interface ShareValueService {

    fun findAllShareValue(user: User): List<ShareValue>

    fun addNewShareValue(user: User)

    fun addNewShareValue(user: User, liquidityMovement: Double)
}
