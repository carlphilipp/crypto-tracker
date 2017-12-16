package fr.cph.crypto.core.spi

import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User

interface ShareValueRepository {

    fun save(shareValue: ShareValue): ShareValue

    fun findAllByUser(user: User): List<ShareValue>

    fun findTop1ByUserOrderByTimestampDesc(user: User): ShareValue?
}