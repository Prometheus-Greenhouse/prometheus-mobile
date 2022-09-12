package tik.prometheus.mobile.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tik.prometheus.mobile.Configs

object RestServiceHelper {
    val baseUrl = Configs.configs?.restServiceHost + ":" + Configs.configs?.restServicePort

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createApi(): RestServiceApi {
        return getRetrofit().create(RestServiceApi::class.java)
    }
}