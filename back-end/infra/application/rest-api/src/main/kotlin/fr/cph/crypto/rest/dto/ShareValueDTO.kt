package fr.cph.crypto.rest.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import fr.cph.crypto.core.api.entity.ShareValue

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "timestamp", "shareQuantity", "shareValue", "originalValue", "gain", "gainPercentage", "positions")
data class ShareValueDTO(var id: String,
                         val timestamp: Long,
                         val shareQuantity: Double,
                         val shareValue: Double,
                         val portfolioValue: Double) {

    companion object {
        fun from(shareValue: ShareValue): ShareValueDTO {
            return ShareValueDTO(
                    id = shareValue.id!!,
                    timestamp = shareValue.timestamp,
                    shareQuantity = shareValue.shareQuantity,
                    shareValue = shareValue.shareValue,
                    portfolioValue = shareValue.portfolioValue)
        }
    }
}