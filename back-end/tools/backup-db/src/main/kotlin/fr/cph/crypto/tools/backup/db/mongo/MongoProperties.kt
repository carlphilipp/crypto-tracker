package fr.cph.crypto.tools.backup.db.mongo

data class MongoProperties(val mongo: Mongo = Mongo()) {
	data class Mongo(
		var mongodumppath: String? = null,
		var exportfolder: String? = null,
		var dbname: String? = null)
}
