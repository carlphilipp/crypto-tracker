package fr.cph.crypto.core.api.entity

data class ShareValue(var id: String? = null,
                      val timestamp: Long,
                      val user: User,
                      val shareQuantity: Double,
                      val shareValue: Double,
                      val portfolioValue: Double)