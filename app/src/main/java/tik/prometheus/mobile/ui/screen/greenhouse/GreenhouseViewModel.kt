package tik.prometheus.mobile.ui.screen.greenhouse

import androidx.lifecycle.ViewModel
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import tik.prometheus.mobile.repository.GreenhouseDataSource
import tik.prometheus.mobile.repository.RestServiceApi
import tik.prometheus.mobile.repository.RestServiceHelper
import tik.prometheus.rest.models.Greenhouse

class GreenhouseViewModel(private val restServiceApi: RestServiceApi = RestServiceHelper.createApi()) : ViewModel() {
    val greenhouses: Flow<PagingData<GreenhouseModel>> = getGreenhouseListStream()
        .map { data ->
            data.map {
                GreenhouseModel.GreenhouseItem(it)
            }
        }
        .map {
            it.insertSeparators { before, after ->
                if (after == null) {
                    return@insertSeparators GreenhouseModel.SeparatorItem("End of list")
                } else if (before == null) {
                    return@insertSeparators null
                } else {
                    null
                }
            }
        }

    private fun getGreenhouseListStream(): Flow<PagingData<Greenhouse>> {
        return Pager(PagingConfig(20)) {
            GreenhouseDataSource(restServiceApi)
        }.flow
    }
}

sealed class GreenhouseModel {
    data class GreenhouseItem(val greenhouse: Greenhouse) : GreenhouseModel()
    data class GreenhouseDetail(val greenhouse: Greenhouse) : GreenhouseModel()
    data class SeparatorItem(val description: String) : GreenhouseModel()
}