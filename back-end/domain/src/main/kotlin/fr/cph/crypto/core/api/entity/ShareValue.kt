package fr.cph.crypto.core.api.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "timestamp", "shareQuantity", "shareValue", "originalValue", "gain", "gainPercentage", "positions")
data class ShareValue(
        var id: String? = null,
        val timestamp: Long,
        @JsonIgnore val user: User,
        val shareQuantity: Double,
        val shareValue: Double,
        val portfolioValue: Double)