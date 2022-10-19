package tik.prometheus.mobile.repository

import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap
import tik.prometheus.mobile.models.Actuator
import tik.prometheus.mobile.models.Page
import tik.prometheus.mobile.models.Sensor
import tik.prometheus.rest.models.Farm
import tik.prometheus.rest.models.Greenhouse

interface RestServiceApi {


    @GET("/farms")
    suspend fun aGetFarms(@QueryMap pageable: Map<String, String>): Response<Page<Farm>>

    @GET("/farms")
    fun getFarms(@QueryMap pageable: Map<String, String>): Single<Page<Farm>>

    @GET("/greenhouses")
    suspend fun getGreenhouses(@QueryMap pageable: Map<String, String>): Response<Page<Greenhouse>>

    @GET("/greenhouses/{id}")
    suspend fun getGreenhouse(@Path("id") id: Long): Response<Greenhouse>

    @GET("/sensors")
    suspend fun getSensors(@QueryMap pageable: Map<String, String>): Response<Page<Sensor>>

    @GET("/sensors/{id}")
    suspend fun getSensor(@Path("id") id: Long): Response<Sensor>

    @GET("/actuators")
    suspend fun getActuators(@QueryMap pageable: Map<String, String>, @Query("greenhouseId") greenhouseId: Long? = null): Response<Page<Actuator>>

    @GET("/actuators/{id}")
    suspend fun getActuator(@Path("id") id: Long): Response<Actuator>

}