package tik.prometheus.mobile.ui.screen.sensor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tik.prometheus.mobile.ZViewModel
import tik.prometheus.mobile.models.Sensor
import tik.prometheus.mobile.repository.RestServiceApi
import tik.prometheus.mobile.repository.RestServiceHelper
import tik.prometheus.mobile.utils.Utils


class SensorDetailViewModel(
    private val restServiceApi: RestServiceApi = RestServiceHelper.createApi()
) : ZViewModel() {

    var sensor: MutableLiveData<Sensor> = MutableLiveData()

    init {
        val sensorId = args[Utils.KEY_SENSOR_ID] as Long
        viewModelScope.launch(Dispatchers.IO) {
            val res = restServiceApi.getSensor(sensorId)
            println(res.body())
            if (res.isSuccessful) {
                sensor.postValue(res.body())
            }
        }
    }

}