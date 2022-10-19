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


class SensorDetailViewModel constructor(
    args: Map<String, Any>,
    restServiceApi: RestServiceApi
) : ZViewModel() {
    constructor(args: Map<String, Any>) : this(args, RestServiceHelper.createApi())

    var sensor: MutableLiveData<Sensor> = MutableLiveData()

    init {
        val sensorId = args[Utils.KEY_SENSOR_ID] as Long
        restServiceApi.let {
            viewModelScope.launch(Dispatchers.IO) {
                val res = it.getSensor(sensorId)
                println(res.body())
                if (res.isSuccessful) {
                    sensor.postValue(res.body())
                }
            }
        }
    }

}