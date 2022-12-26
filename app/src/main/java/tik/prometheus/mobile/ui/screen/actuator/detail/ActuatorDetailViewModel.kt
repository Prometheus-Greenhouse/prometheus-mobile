package tik.prometheus.mobile.ui.screen.actuator.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import ir.mirrajabi.searchdialog.core.Searchable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tik.prometheus.mobile.ZViewModel
import tik.prometheus.mobile.models.*
import tik.prometheus.mobile.repository.ActuatorRepository
import tik.prometheus.mobile.utils.RunOn
import tik.prometheus.mobile.utils.ZLoadState
import tik.prometheus.mobile.utils.toExcept

class ActuatorDetailViewModel(private val actuatorRepository: ActuatorRepository) : ZViewModel() {
    val TAG = ActuatorDetailViewModel::class.java.toString()
    var loadState: MutableLiveData<ZLoadState> = MutableLiveData(ZLoadState.NOT_LOADING)
    var actuatorId: MutableLiveData<Long> = MutableLiveData()
    var actuator: MutableLiveData<Actuator> = MutableLiveData()
    var actuatorTask: MutableLiveData<ActuatorTask?> = MutableLiveData()
    var isRunning: MutableLiveData<Boolean> = MutableLiveData(false)
    var sensors: MutableLiveData<List<Sensor>> = MutableLiveData(arrayListOf())

    fun fetchSensors(): List<Sensor> {
        actuator.value?.greenhouse.let {
            viewModelScope.launch(Dispatchers.IO) {
                val res = actuatorRepository.restServiceApi.getSensors(
                    Pageable(page = 0, size = 100).toMap(), actuator.value?.greenhouse?.id
                );
                if (res.isSuccessful) {
                    sensors.postValue(res.body()?.content ?: arrayListOf())
                }
            }
        }
        return arrayListOf();
    }

    fun fetchActuatorDetail() {
        loadState.postValue(ZLoadState.LOADING)

        viewModelScope.launch(Dispatchers.IO) {
            println("45: fetchActuatorDetail" + actuatorId.value)
            val res = actuatorRepository.restServiceApi.getActuator(actuatorId.value!!)
            if (res.isSuccessful) {
                fetchActuatorTask()
                val actuatorRes = res.body()!!
                actuator.postValue(actuatorRes)
                isRunning.postValue(actuatorRes.state.running)
                loadState.postValue(ZLoadState.NOT_LOADING)
            } else {
                loadState.postValue(ZLoadState.Error(Exception(res.toExcept().message)))
            }
        }
    }

    fun fetchActuatorTask() {
        Log.d(TAG, "fetchActuatorTask: " + actuatorId.value)
        viewModelScope.launch(Dispatchers.IO) {
            val res = actuatorRepository.restServiceApi.getActuatorTask(actuatorId.value!!)
            if (res.isSuccessful) {
                actuatorTask.postValue(res.body())
            } else {
                actuatorTask.postValue(null)
            }
        }
    }

    fun postRunning(isRunning: Boolean) {
        this.isRunning.postValue(isRunning)
    }
    fun postActuatorTask(actuatorTask: ActuatorTask){
        this.actuatorTask.postValue(actuatorTask)
    }
    fun toggleRunning() {
        viewModelScope.launch(Dispatchers.IO) {
            val nextIsRunning = isRunning.value!!.not()
            actuatorRepository.restServiceApi.patchActuator(
                actuator.value!!.id,
                ActuatorState(
                    running = nextIsRunning
                )
            )
        }
    }

    fun saveActuator() {
        loadState.postValue(ZLoadState.LOADING)
        viewModelScope.launch(Dispatchers.IO) {
            println(actuatorId.value)
            val res = actuatorRepository.restServiceApi.putActuator(actuatorId.value!!, actuator.value!!.toActuatorReq())
            println(actuator.value)
            println(res.raw())
            if (res.isSuccessful) {
                if (actuatorTask.value == null) {
                    val res = actuatorRepository.restServiceApi.deleteActuatorTask(actuatorId.value!!)
                    println(res.message())
                    if (!res.isSuccessful) {
                        val except = res.toExcept()
                        loadState.postValue(ZLoadState.Error(Exception(except.message)))
                    }
                } else {
                    println(actuatorId.value)
                    println(actuatorTask.value)
                    val res = actuatorRepository.restServiceApi.postActuatorTask(actuatorId.value!!, actuatorTask.value!!)
                    println(res.raw())
                    if (!res.isSuccessful) {
                        val except = res.toExcept()
                        loadState.postValue(ZLoadState.Error(Exception(except.message)))
                    }
                }
                loadState.postValue(ZLoadState.NOT_LOADING)
                actuator.postValue(res.body())

            } else {
                val except = res.toExcept()
                loadState.postValue(ZLoadState.Error(Exception(except.message)))
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class Factory(private val actuatorRepository: ActuatorRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ActuatorDetailViewModel(actuatorRepository) as T
        }
    }
}

sealed class RunOnModel {
    data class RunOnItem(val value: RunOn) : Searchable {
        override fun getTitle(): String {
            return value.name.lowercase().replace("_", " ")
        }
    }

}