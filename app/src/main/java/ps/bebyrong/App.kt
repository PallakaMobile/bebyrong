package ps.bebyrong

import android.content.Context
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.crashlytics.android.Crashlytics
import com.github.ajalt.timberkt.Timber
import com.github.ajalt.timberkt.e
import io.fabric.sdk.android.Fabric
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ps.bebyrong.di.baseApp
import timber.log.Timber.DebugTree


/**
 * *********************************************
 * Created by ukie on 9/26/18 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 * *********************************************
 * © 2018 | All Right Reserved
 */
class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        //enable crashlytics in debugging
        val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(true)           // Enables Crashlytics debugger
                .build()
        Fabric.with(fabric)

        //Insert Koin
        startKoin {
            e { "koin start" }
            androidContext(this@App)
            modules(baseApp)
        }

    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}
