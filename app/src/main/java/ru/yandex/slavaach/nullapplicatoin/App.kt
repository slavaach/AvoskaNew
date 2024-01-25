package ru.yandex.slavaach.nullapplicatoin

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.yandex.slavaach.nullapplicatoin.di.appModule
import ru.yandex.slavaach.nullapplicatoin.di.databaseModule
import ru.yandex.slavaach.nullapplicatoin.di.networkModule
import ru.yandex.slavaach.nullapplicatoin.features.buy.buyModule
import ru.yandex.slavaach.nullapplicatoin.features.custom.customModule
import ru.yandex.slavaach.nullapplicatoin.features.reference.referenceModule
import ru.yandex.slavaach.nullapplicatoin.features.weather.weatherModule
import ru.yandex.slavaach.nullapplicatoin.services.AppUnhandledExceptionHandler
import timber.log.Timber
import kotlin.reflect.full.createInstance

class App : Application() {

    companion object {
        var marketModule: MarketModule? = null
    }

    override fun onCreate() {
        super.onCreate()
        Timber.tag("7777").v("%s onCreate", javaClass.name)
        setUnhandledExHanlder()
        when (BuildConfig.MARKET_KEY) {
            "rustore" -> {
                marketModule = Class.forName("ru.yandex.slavaach.nullapplicatoin.RuModule").kotlin.createInstance() as MarketModule
            }
            "google" -> {
                marketModule = Class.forName("ru.yandex.slavaach.nullapplicatoin.GoogleModule").kotlin.createInstance() as MarketModule
            }
        }

        initKoin()

        AndroidThreeTen.init(this)

        if (BuildConfig.DEBUG) {
            // Stetho.initializeWithDefaults(this)
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        val pushHelper = marketModule?.getPushHelper()
        pushHelper?.initPushes(this)
        // pushHelper?.createNotificationPushChannel(this)
        // createNotificationChannel()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(modules)
        }
    }

    val modules = listOf(
        appModule,
        databaseModule,
        networkModule,
        weatherModule,
        referenceModule,
        customModule,
        buyModule,
    )

    private fun setUnhandledExHanlder() {
        val currentHanlder = Thread.getDefaultUncaughtExceptionHandler()
        if (currentHanlder !is AppUnhandledExceptionHandler) {
            Thread.setDefaultUncaughtExceptionHandler(AppUnhandledExceptionHandler(this.javaClass.name))
        }
    }
}
