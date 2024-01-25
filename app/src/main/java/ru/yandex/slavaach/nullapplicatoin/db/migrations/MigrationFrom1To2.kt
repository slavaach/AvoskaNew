package ru.yandex.slavaach.nullapplicatoin.db.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class MigrationFrom1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        val tempTableName = "temp_full"
        // database.execSQL("CREATE TABLE IF NOT EXISTS `$tempTableName` (`memberId` TEXT, `name` TEXT, `fullName` TEXT NOT NULL, `engName` TEXT, `rusName` TEXT, `bic` TEXT NOT NULL, `md5hash` TEXT NOT NULL, `svgImage` TEXT, `fiasId` TEXT, `address` TEXT, `latitude` TEXT, `longitude` TEXT, `swiftList` TEXT, `inn` TEXT, `kpp` TEXT, `registrationNumber` TEXT, `registrationDate` TEXT, `bankType` TEXT, `bankTypeCode` TEXT, `bankServiceType` TEXT, `bankServiceTypeCode` TEXT, `accountList` TEXT, PRIMARY KEY(`bic`))")
        // database.execSQL("INSERT or REPLACE INTO '$tempTableName' SELECT `memberId`, `name`, `fullName`, `engName` TEXT, `rusName` TEXT, `bic`, `md5hash`, `svgImage`, `fiasId`, `address`, `latitude`, `longitude`, `swiftList`, `inn`, `kpp`, `registrationNumber`, `registrationDate`, `bankType`, `bankTypeCode`, `bankServiceType`, `bankServiceTypeCode`, `accountList` FROM `full_info_banks`")
        // database.execSQL("DROP TABLE `full_info_banks`")
        // database.execSQL("ALTER TABLE '$tempTableName' RENAME TO 'full_info_banks'")
    }
}
