package tik.prometheus.mobile.ui.screen.sensor

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tik.prometheus.mobile.ZViewModel
import tik.prometheus.mobile.models.Sensor
import tik.prometheus.mobile.repository.RestServiceApi
import tik.prometheus.mobile.repository.RestServiceHelper
import tik.prometheus.mobile.utils.*


class SensorDetailViewModel constructor(
    args: Map<String, Any>,
    restServiceApi: RestServiceApi
) : ZViewModel() {
    constructor(args: Map<String, Any>) : this(args, RestServiceHelper.createApi())

    var sensor: MutableLiveData<Sensor> = MutableLiveData()
//    var units: MutableLiveData<ArrayList<UnitModel>> = MutableLiveData(arrayListOf())

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
//        getUnits()
    }

//    fun getUnits() {
//        val units = EUnit.values();
//        val groups = UnitGroup.values()
//
//        val unitModels = arrayListOf<UnitModel>()
//        for (g in groups) {
//            unitModels.add(UnitModel.GroupItem(g))
//            // ANY
//            val items = units.filter { it.group == g && it.system == UnitSystem.ANY }.map { UnitModel.UnitItem(it) }
//            unitModels.addAll(items)
//
//            // Imperial
//            val imperialItems = units.filter { it.group == g && it.system == UnitSystem.IMPERIAL }.map { UnitModel.UnitItem(it) }
//            if (imperialItems.isNotEmpty()) {
//                unitModels.add(UnitModel.SystemItem(UnitSystem.IMPERIAL))
//                unitModels.addAll(imperialItems)
//            }
//
//            // Metric
//            val metricItems = units.filter { it.group == g && it.system == UnitSystem.METRIC }.map { UnitModel.UnitItem(it) }
//            if (metricItems.isNotEmpty()) {
//                unitModels.add(UnitModel.SystemItem(UnitSystem.METRIC))
//                unitModels.addAll(metricItems)
//            }
//
//        }
//        this.units.postValue(unitModels)
//    }
}