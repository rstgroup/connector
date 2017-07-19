package com.rstit.connector.di.base

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.rstit.connector.BuildConfig
import com.rstit.connector.net.ConnectorApi
import com.rstit.connector.settings.AppSettings
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * @author Tomasz Trybala & Marcin Przepiórkowski
 * @since 2017-07-18
 */

const val API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
const val HEADER_AUTHORIZATION = "Auth-Token"
const val MAX_TIMEOUT = 60L

@Module
class NetModule {
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
            .setDateFormat(API_DATE_FORMAT)
            .create()

    @Provides
    @Singleton
    fun provideOkHttpClient(appSettings: AppSettings): OkHttpClient {
        val builder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(logging)
        }

        builder.addInterceptor { chain ->
            val original = chain.request()
            val token = appSettings.apiToken

            if (!token.isNullOrEmpty()) {
                val request = original.newBuilder()
                        .header(HEADER_AUTHORIZATION, token)
                        .method(original.method(), original.body())
                        .build()

                try {
                    return@addInterceptor chain.proceed(request)
                } catch (ioe: IOException) {
                    return@addInterceptor chain.proceed(original.newBuilder().build())
                }

            } else {
                return@addInterceptor chain.proceed(original.newBuilder().build())
            }
        }

        builder.connectTimeout(MAX_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(MAX_TIMEOUT, TimeUnit.SECONDS)

        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, client: OkHttpClient): Retrofit =
            Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(BuildConfig.API_URL)
                    .client(client)
                    .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): ConnectorApi = retrofit.create(ConnectorApi::class.java)
}