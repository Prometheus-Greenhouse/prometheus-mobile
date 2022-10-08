package tik.prometheus.mobile.ui.screen.greenhouse

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.LoadState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tik.prometheus.mobile.repository.RestServiceApi
import tik.prometheus.mobile.repository.RestServiceHelper
import tik.prometheus.rest.models.Greenhouse

class GreenhouseDetailViewModel(private val restServiceApi: RestServiceApi = RestServiceHelper.createApi()) : ViewModel() {
    private val TAG = GreenhouseDetailViewModel::class.toString()
    var loadState: MutableLiveData<LoadState> = MutableLiveData()

    var greenhouse: MutableLiveData<Greenhouse> = MutableLiveData()

//    = liveData {
//        loadState.value = LoadState.Loading
//        val data = getGreenhouseDetail(1);
//        loadState.value = LoadState.NotLoading(true)
//        if (data.isSuccessful) {
//            Log.d(TAG, data.body().toString())
//            emit(data.body()!!)
//        } else {
//            loadState.value = LoadState.Error(Exception(data.errorBody().toString()));
//        }
//    }

    fun getGreenhouseDetail(id: Long) {
        loadState.postValue(LoadState.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            val res = restServiceApi.getGreenhouse(id)
            if (res.isSuccessful) {
                greenhouse.postValue(res.body())
                loadState.postValue(LoadState.NotLoading(true))
            } else {
                println(res.errorBody()?.string())
                loadState.postValue(LoadState.Error(Exception(res.errorBody().toString())))
            }
        }
    }

}