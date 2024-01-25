package ru.yandex.slavaach.nullapplicatoin.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import ru.yandex.slavaach.nullapplicatoin.db.AppDatabase
import ru.yandex.slavaach.nullapplicatoin.db.dao.SettingHomeDao
import ru.yandex.slavaach.nullapplicatoin.db.migrations.MigrationFrom1To2
import ru.yandex.slavaach.nullapplicatoin.features.custom.data.CustomDao
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.home.HomeDao
import ru.yandex.slavaach.nullapplicatoin.features.reference.data.sale.SaleDao

val databaseModule = module {

    val ALL_MIGRATIONS = arrayOf<Migration>(
        MigrationFrom1To2(),
    )

    fun provideDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "null-database")
            // .addMigrations(*ALL_MIGRATIONS)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideHomeDao(database: AppDatabase): HomeDao {
        return database.homeDao
    }

    fun provideSaleDao(database: AppDatabase): SaleDao {
        return database.saleDao
    }

    fun provideCustomDao(database: AppDatabase): CustomDao {
        return database.customDao
    }

    fun provideSettingHomeDao(database: AppDatabase): SettingHomeDao {
        return database.settingHomeDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideHomeDao(get()) }
    single { provideSaleDao(get()) }
    single { provideCustomDao(get()) }
    single { provideSettingHomeDao(get()) }
}
