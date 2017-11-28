package fr.cph.crypto.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("id", "email", "role", "positions")
@Document
data class User(@Indexed(unique = true) val email: String,
                @JsonIgnore var password: String,
                val role: Role = Role.USER) {

    @Id
    var id: String? = null

    @DBRef
    var positions: List<Position> = listOf()
}
