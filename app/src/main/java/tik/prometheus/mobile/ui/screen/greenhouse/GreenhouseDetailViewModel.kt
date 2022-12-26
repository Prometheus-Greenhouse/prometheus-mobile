package tik.prometheus.mobile.ui.screen.greenhouse

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tik.prometheus.mobile.ZViewModel
import tik.prometheus.mobile.repository.GreenhouseRepository
import tik.prometheus.rest.models.Greenhouse

class GreenhouseDetailViewModel(private val greenhouseRepository: GreenhouseRepository) : ZViewModel() {

    private val TAG = GreenhouseDetailViewModel::class.toString()
    var loadState: MutableLiveData<LoadState> = MutableLiveData()

    var greenhouse: MutableLiveData<Greenhouse> = MutableLiveData()

    fun getGreenhouseDetail(id: Long) {
        loadState.postValue(LoadState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            val res = greenhouseRepository.restServiceApi.getGreenhouse(id)
            if (res.isSuccessful) {
                System.out.println("get greenhouse detail: " + res.body().toString())
                greenhouse.postValue(res.body())
                loadState.postValue(LoadState.NotLoading(true))
            } else {
                println(res.errorBody()?.string())
                loadState.postValue(LoadState.Error(Exception(res.errorBody().toString())))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(private val greenhouseRepository: GreenhouseRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GreenhouseDetailViewModel(greenhouseRepository) as T
        }
    }
}