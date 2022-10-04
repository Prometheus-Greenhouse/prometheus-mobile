package tik.prometheus.mobile.repository

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import tik.prometheus.mobile.models.Pageable
import tik.prometheus.mobile.models.Sensor

class SensorDataSource(
    private val restServiceApi: RestServiceApi,
) : PagingSource<Int, Sensor>() {
    val TAG = SensorDataSource::class.toString()
    override fun getRefreshKey(state: PagingState<Int, Sensor>): Int {
        return 0;
    }

    override suspend fun load(params: PagingSource.LoadParams<Int>): PagingSource.LoadResult<Int, Sensor> {
        val nextPage = params.key ?: 0
        val res = restServiceApi.getSensors(Pageable(page = nextPage, size = 10).toMap())
        if (res.isSuccessful) {
            val pageSensor = res.body()
            return LoadResult.Page(
                data = pageSensor?.content!!,
                prevKey = if (nextPage == 0) null else nextPage - 1,
                nextKey = if (nextPage < pageSensor.totalPages) pageSensor.pageable.pageNumber.plus(1) else null
            )
        } else {
            Log.d(TAG, "load: failed")
            return LoadResult.Error(java.lang.Exception(res.errorBody().toString()))
        }
    }


}
