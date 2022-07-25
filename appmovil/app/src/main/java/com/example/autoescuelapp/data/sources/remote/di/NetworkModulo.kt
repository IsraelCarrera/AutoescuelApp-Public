package com.example.autoescuelapp.data.sources.remote.di

import com.example.autoescuelapp.data.sources.remote.*
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModulo {
    @Singleton
    @Provides
    fun provideServiceInterceptor(): ServiceInterceptor = ServiceInterceptor()


    @Singleton
    @Provides
    fun provideHttpClient(serviceInterceptor: ServiceInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(serviceInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory =
        GsonConverterFactory.create()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
    ): Retrofit {
        val g = Gson()
        return Retrofit.Builder()
            .baseUrl("http://informatica.iesquevedo.es:2326/AutoescuelAPP/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(g))
            .addConverterFactory(gsonConverterFactory)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
    }

    //Las interfaces de la api a la que se llama.
    @Singleton
    @Provides
    fun provideCurrencyServiceApiPersona(retrofit: Retrofit): PersonaAPI =
        retrofit.create(PersonaAPI::class.java)

    @Singleton
    @Provides
    fun provideCurrencyServiceApiClase(retrofit: Retrofit): ClaseAPI =
        retrofit.create(ClaseAPI::class.java)

    @Singleton
    @Provides
    fun provideCurrencyServiceApiCoche(retrofit: Retrofit): CocheAPI =
        retrofit.create(CocheAPI::class.java)

    @Singleton
    @Provides
    fun provideCurrencyServiceApiIncidencia(retrofit: Retrofit): IncidenciaAPI =
        retrofit.create(IncidenciaAPI::class.java)

    @Singleton
    @Provides
    fun provideCurrencyServiceApiFeedback(retrofit: Retrofit): FeedbackAPI =
        retrofit.create(FeedbackAPI::class.java)
}