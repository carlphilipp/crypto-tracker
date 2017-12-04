package fr.cph.crypto.backend.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "email", "value", "originalValue", "gain", "gainPercentage", "positions")
@Document
data class User(@Indexed(unique = true) val email: String,
                @JsonIgnore var password: String,
                @JsonIgnore val role: Role = Role.USER) {

    @Id
    var id: String? = null

    // Calculated fields
    @Transient
    var value: Double? = null
    @Transient
    var originalValue: Double? = null
    @Transient
    var gain: Double? = null
    @Transient
    var gainPercentage: Double? = null

    @DBRef
    var positions: MutableList<Position> = mutableListOf()
}
