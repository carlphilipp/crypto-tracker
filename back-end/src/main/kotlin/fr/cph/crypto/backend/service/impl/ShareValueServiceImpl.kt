package fr.cph.crypto.backend.service.impl

import fr.cph.crypto.backend.domain.ShareValue
import fr.cph.crypto.backend.domain.User
import fr.cph.crypto.backend.repository.ShareValueRepository
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
            val shareValueToSave = ShareValue(
                    timestamp = System.currentTimeMillis(),
                    user = user,
                    shareQuantity = user.value!! / 100,
            console.log("name: " + name + " value: " + value)              shareValue = 100.0,
                    portfolioValue = user.value!!)
            shareValueRepository.save(shareValueToSave)
        } else {
            LOGGER.info("Last share value: {}" + lastShareValue)
            LOGGER.info("User portfolio value: {}" + user.value)
            val quantity = lastShareValue.shareQuantity + (user.liquidityMovement) / ((user.value!! - user.liquidityMovement) / lastShareValue.shareQuantity)
            LOGGER.info("Quantity: {}" + quantity)
            val shareValue = user.value!! / quantity
            LOGGER.info("Share value: {}" + shareValue)

            val shareValueToSave = ShareValue(
                    timestamp = System.currentTimeMillis(),
                    user = user,
                    shareQuantity = quantity,
                    shareValue = shareValue,
                    portfolioValue = user.value!!)
            shareValueRepository.save(shareValueToSave)
        }
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(ShareValueServiceImpl::class.java)
    }
}