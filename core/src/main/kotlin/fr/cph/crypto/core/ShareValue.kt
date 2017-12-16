package fr.cph.crypto.core

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "timestamp", "shareQuantity", "shareValue", "originalValue", "gain", "gainPercentage", "positions")
@Document(collection = "share_value")
data class ShareValue(
        @Id var id: String? = null,
        val timestamp: Long,
        @JsonIgnore @Indexed @DBRef val user: User,
        val shareQuantity: Double,
        val shareValue: Double,
        val portfolioValue: Double)