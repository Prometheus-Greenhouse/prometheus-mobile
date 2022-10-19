package tik.prometheus.mobile.ui.screen.actuator.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tik.prometheus.mobile.ZViewModel
import tik.prometheus.mobile.models.Actuator
import tik.prometheus.mobile.repository.ActuatorRepository
import tik.prometheus.mobile.ui.screen.actuator.ActuatorViewModel
import tik.prometheus.mobile.utils.toExcept

class ActuatorDetailViewModel(private val actuatorRepository: ActuatorRepository) : ZViewModel() {
    var loadState: MutableLiveData<LoadState> = MutableLiveData()
    var actuator: MutableLiveData<Actuator> = MutableLiveData()

    fun getActuatorDetail(id: Long) {
        loadState.postValue(LoadState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            val res = actuatorRepository.restServiceApi.getActuator(id)
            if (res.isSuccessful) {
                actuator.postValue(res.body())
                loadState.postValue(LoadState.NotLoading(true))
            } else {
                loadState.postValue(LoadState.Error(Exception(res.toExcept().message)))
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
