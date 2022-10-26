package tik.prometheus.mobile.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import tik.prometheus.mobile.models.Actuator

class ActuatorRepository(private val actuatorDataSource: ActuatorDataSource, val restServiceApi: RestServiceApi) {
    private val TAG = ActuatorRepository::class.toString()

    fun getActuatorListStream(greenhouseId: Long? = null): Flow<PagingData<Actuator>> {
        actuatorDataSource.greenhouseId = greenhouseId
        return Pager(PagingConfig(20)) { actuatorDataSource }.flow
    }

}