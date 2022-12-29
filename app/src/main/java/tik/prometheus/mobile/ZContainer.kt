package tik.prometheus.mobile

import tik.prometheus.mobile.repository.*

class ZContainer {

    val restServiceApi = RestServiceHelper.createApi()

    val greenhouseDataSource = GreenhouseDataSource(restServiceApi)

    val actuatorDataSource = ActuatorDataSource(restServiceApi)

    val greenhouseRepository = GreenhouseRepository(greenhouseDataSource, restServiceApi)

    val sensorRepository = SensorRepository(restServiceApi)

    val actuatorRepository = ActuatorRepository(actuatorDataSource, restServiceApi)
}