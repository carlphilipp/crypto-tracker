package fr.cph.crypto.core.spi

interface IdGenerator {
    fun getNewId(): String
}