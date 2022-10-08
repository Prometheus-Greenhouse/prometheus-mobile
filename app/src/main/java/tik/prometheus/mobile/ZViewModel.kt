package tik.prometheus.mobile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class ZViewModel(
    val args: MutableMap<String, Any?> = mutableMapOf()
) : ViewModel()

class ZViewModelFactory(val map: MutableMap<String, Any?>) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MutableMap::class.java).newInstance(map) as T
    }

}
