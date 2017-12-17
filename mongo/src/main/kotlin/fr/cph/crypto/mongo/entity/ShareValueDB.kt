package fr.cph.crypto.mongo.entity

import fr.cph.crypto.core.api.entity.ShareValue
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.DBRef
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "share_value")
data class ShareValueDB(
        @Id var id: String? = null,
        val timestamp: Long,
        @Indexed @DBRef val user: UserDB,
        val shareQuantity: Double,
        val shareValue: Double,
        val portfolioValue: Double) {

    fun toShareValue(): ShareValue {
        val shareValue = ShareValue(
                timestamp = this.timestamp,
                user = this.user.toUser(),
                shareQuantity = this.shareQuantity,
                shareValue = this.shareValue,
                portfolioValue = this.portfolioValue
        )
        shareValue.id = this.id
        return shareValue
    }

    companion object {
        fun toShareValueDB(shareValue: ShareValue): ShareValueDB {
            return ShareValueDB(
                    id = shareValue.id,
                    timestamp = shareValue.timestamp,
                    user = fr.cph.crypto.mongo.entity.UserDB.toDbUser(shareValue.user),
                    shareQuantity = shareValue.shareQuantity,
                    shareValue = shareValue.shareValue,
                    portfolioValue = shareValue.portfolioValue
            )
        }
    }
}