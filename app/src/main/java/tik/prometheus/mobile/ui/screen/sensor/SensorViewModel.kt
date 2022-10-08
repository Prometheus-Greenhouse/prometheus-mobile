package tik.prometheus.mobile.ui.screen.sensor

import androidx.lifecycle.ViewModel
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.eclipse.paho.android.service.MqttAndroidClient
import tik.prometheus.mobile.models.Sensor
import tik.prometheus.mobile.repository.RestServiceApi
import tik.prometheus.mobile.repository.RestServiceHelper
import tik.prometheus.mobile.repository.SensorDataSource

class HomeViewModel(private val restServiceApi: RestServiceApi = RestServiceHelper.createApi()) : ViewModel() {
    val sensors: Flow<PagingData<SensorModel>> = getSensorListStream()
        .map { data ->
            data.map {
                SensorModel.SensorItem(it)
            }
        }
        .map {
            it.insertSeparators<SensorModel.SensorItem, SensorModel>() { before, after ->
                if (after == null) {
                    return@insertSeparators SensorModel.SeparatorItem("End of list")
                } else if (before == null) {
                    return@insertSeparators null
                } else {
                    null
                }
            }
        }


    private fun getSensorListStream(): Flow<PagingData<Sensor>> {
        return Pager(PagingConfig(20)) {
            SensorDataSource(restServiceApi)
        }.flow
    }
}

sealed class SensorModel {
    data class SensorItem(val sensor: Sensor, var mqttClient: MqttAndroidClient? = null) : SensorModel()
    data class SeparatorItem(val description: String) : SensorModel()
}

//private val SensorModel.SensorItem.roundedVoteCount: Int
//    get() = this.sensor.