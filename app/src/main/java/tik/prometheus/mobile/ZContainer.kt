package tik.prometheus.mobile

import tik.prometheus.mobile.repository.*

class ZContainer {

    val restServiceApi = RestServiceHelper.createApi()


    val farmDataSource = FarmDataSource(restServiceApi)

    val greenhouseDataSource = GreenhouseDataSource(restServiceApi)

    val sensorDataSource = SensorDataSource(restServiceApi)

    val actuatorDataSource = ActuatorDataSource(restServiceApi)

    val greenhouseRepository = GreenhouseRepository(greenhouseDataSource, restServiceApi)

    val sensorRepository = SensorRepository(sensorDataSource)

    val actuatorRepository = ActuatorRepository(actuatorDataSource, restServiceApi)
}