package fr.cph.crypto.core.api.exception

internal class NotFoundException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
}
