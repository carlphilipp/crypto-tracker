package fr.cph.crypto.tools.export.db.mongo

data class MongoProperties(val mongo: Mongo = Mongo()) {
	data class Mongo(
		var mongodumppath: String? = null,
		var dbname: String? = null)
}
