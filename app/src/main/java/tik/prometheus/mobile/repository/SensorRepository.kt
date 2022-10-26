package tik.prometheus.mobile.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tik.prometheus.mobile.models.Sensor

class SensorRepository(private val sensorDataSource: SensorDataSource, val restServiceApi: RestServiceApi) {

    fun getSensorListStream(greenhouseId: Long? = null): Flow<PagingData<Sensor>> {
        sensorDataSource.greenhouseId = greenhouseId
        return Pager(PagingConfig(20)) { sensorDataSource }.flow
    }
}