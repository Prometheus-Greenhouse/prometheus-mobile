package tik.prometheus.mobile.repository

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import tik.prometheus.mobile.models.Page
import tik.prometheus.mobile.models.Sensor
import tik.prometheus.rest.models.Farm
import tik.prometheus.rest.models.Greenhouse

interface RestServiceApi {
    @GET("/sensors")
    suspend fun getSensors(@QueryMap pageable: Map<String, String>): Response<Page<Sensor>>

    @GET("/farms")
    suspend fun agetFarms(@QueryMap pageable: Map<String, String>): Response<Page<Farm>>

    @GET("/farms")
    fun getFarms(@QueryMap pageable: Map<String, String>): Single<Page<Farm>>

    @GET("/greenhouses")
    suspend fun getGreenhouses(@QueryMap pageable: Map<String, String>): Response<Page<Greenhouse>>

}