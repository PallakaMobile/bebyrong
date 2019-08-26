package ps.bebyrong.di

import android.content.Context
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ps.bebyrong.BuildConfig
import ps.bebyrong.data.source.rest.BebyrongAPI
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


/**
 **********************************************
 * Created by ukie on 10/4/18 with ♥
 * (>’_’)> email : ukie.tux@gmail.com
 * github : https://www.github.com/tuxkids <(’_’<)
 **********************************************
 * © 2018 | All Right Reserved
 */

val networkModule = module(createdAtStart = true) {
    single { createOkhttpClient(androidContext()) }
    single { createWebService<BebyrongAPI>(get()) }
}

fun createOkhttpClient(context: Context): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

//    val cacheSize = (5 * 1024 * 1024).toLong()
//    val myCache = Cache(context.cacheDir, cacheSize)

    val builder = OkHttpClient.Builder()
//    builder.cache(myCache)
    builder.connectTimeout(2, TimeUnit.MINUTES)
    builder.readTimeout(2, TimeUnit.MINUTES)
    builder.writeTimeout(2, TimeUnit.MINUTES)
    builder.hostnameVerifier { _, _ -> true }

    builder.retryOnConnectionFailure(true)
    builder.addInterceptor(loggingInterceptor)
    builder.addInterceptor { chain ->
        val request = chain.request().newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json")
//                .addHeader("Cache-Control", "public, max-age=" + 60)
        chain.proceed(request.build())
    }
    return builder.build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient): T {
    val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    return retrofit.create(T::class.java)
}
