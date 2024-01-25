package ru.yandex.slavaach.nullapplicatoin.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.yandex.slavaach.nullapplicatoin.db.converter.Converters
import ru.yandex.slavaach.nullapplicatoin.db.converter.StringToListIntConverter
import ru.yandex.slavaach.nullapplicatoin.db.dao.SettingHomeDao
import ru.yandex.slavaach.nullapplicatoin.db.entity.SettingHome
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.Custom
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.CustomDao
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.home.Home
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.home.HomeDao
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale.Sale
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale.SaleDao

@Database(
    entities = [
        Home::class,
        Sale::class,
        Custom::class,
        SettingHome::class,
    ],
    version = 3,
    exportSchema = true,
)
@TypeConverters(
    Converters::class,
    StringToListIntConverter::class,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val homeDao: HomeDao
    abstract val saleDao: SaleDao
    abstract val customDao: CustomDao
    abstract val settingHomeDao: SettingHomeDao
}
