package fr.cph.crypto.backend.service.impl

import fr.cph.crypto.core.api.entity.ShareValue
import fr.cph.crypto.core.api.entity.User
import fr.cph.crypto.core.spi.ShareValueRepository
import fr.cph.crypto.backend.service.ShareValueService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ShareValueServiceImpl(private val shareValueRepository: ShareValueRepository) : ShareValueService {

    override fun findAllShareValue(user: User): List<ShareValue> {
        return shareValueRepository.findAllByUser(user)
    }

    override fun addNewShareValue(user: User) {
        addNewShareValue(user, 0.0)
    }

    override fun addNewShareValue(user: User, liquidityMovement: Double) {
        val lastShareValue = shareValueRepository.findTop1ByUserOrderByTimestampDesc(user)
        if (lastShareValue == null) {
            addFirstTimeShareValue(user)
        } else {
            val quantity = lastShareValue.shareQuantity + (user.liquidityMovement) / ((user.value!! - user.liquidityMovement) / lastShareValue.shareQuantity)
            val shareValue = user.value!! / quantity
            val shareValueToSave = ShareValue(
                    timestamp = System.currentTimeMillis(),
                    user = user,
                    shareQuantity = quantity,
                    shareValue = shareValue,
                    portfolioValue = user.value!!)
            shareValueRepository.save(shareValueToSave)
        }
    }

    /**
     * For the first time,
     */
    private fun addFirstTimeShareValue(user: User) {
        val yesterdayShareValue = ShareValue(
                timestamp = System.currentTimeMillis() - 86400000,
                user = user,
                shareQuantity = user.originalValue!! / 100,
                shareValue = 100.0,
                portfolioValue = user.originalValue!!)
        shareValueRepository.save(yesterdayShareValue)
        val defaultShareValue = user.gainPercentage!! * 100 + 100
        val shareValueToSave = ShareValue(
                timestamp = System.currentTimeMillis(),
                user = user,
                shareQuantity = user.value!! / defaultShareValue,
                shareValue = defaultShareValue,
                portfolioValue = user.value!!)
        shareValueRepository.save(shareValueToSave)
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ShareValueServiceImpl::class.java)
    }
}