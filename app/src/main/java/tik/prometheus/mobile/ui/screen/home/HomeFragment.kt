package tik.prometheus.mobile.ui.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tik.prometheus.mobile.R
import tik.prometheus.mobile.databinding.FragmentHomeBinding
import tik.prometheus.mobile.ui.adapters.SensorAdapter
import tik.prometheus.mobile.ui.adapters.SensorLoadStateAdapter
import tik.prometheus.mobile.utils.themeColor

class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHomeBinding
    private var mpLinechart: LineChart? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val sensorAdapter = SensorAdapter()

        binding.sensorsRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = sensorAdapter.withLoadStateFooter(
                footer = SensorLoadStateAdapter { sensorAdapter.retry() }
            )
        }
        lifecycleScope.launch {
            viewModel.sensors.collectLatest {
                sensorAdapter.submitData(it)
            }
        }

        binding.btnRetry.setOnClickListener {
            sensorAdapter.retry()
        }

        sensorAdapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.btnRetry.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> {
                        binding.btnRetry.visibility = View.VISIBLE
                        loadState.refresh as LoadState.Error
                    }

                    else -> null
                }
                errorState?.let {
                    showToast(it.error.message.toString())
                }
            }
        }
        return binding.root
    }
//
//
//    private fun initSensorDashboard() {
//        val sensorRecycleView = binding!!.sensorsRecyclerView
//        sensorRecycleView.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
//        val sensorAdapter = SensorAdapter {}
//        sensorRecycleView.adapter = sensorAdapter
//        val restServiceApi = RestServiceHelper.getInstance().create(RestServiceApi::class.java)
//        GlobalScope.launch {
//            val queryMap = Pageable(0, 10, ArrayList()).toMap()
//            val res = restServiceApi.getSensors(queryMap)
//            if (res.isSuccessful) {
//                val sensorPage = res.body();
//                for (sensor: Sensor in sensorPage?.content!!) {
//                    val card = CardView(requireContext())
//                    val layout = LinearLayout.LayoutParams(200, 200)
//                    layout.setMargins(5, 5, 5, 5)
//                    card.layoutParams = layout
//                    val color: Int = requireContext().themeColor(R.attr.colorError)
//                    card.setCardBackgroundColor(color)
//                    val txt = TextView(requireContext())
//                    txt.text = sensor.topic
//                    card.addView(txt)
//                    viewModel?.sensorMap?.value?.put(sensor.id, sensor)
//                    sensorRecycleView.addView(card)
//                }
//            }
//        }
//    }

    private fun initChart() {
        mpLinechart = binding!!.lineChart
        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(
            LineDataSet(dataValues(), "Data set 1")
        )
        val data = LineData(dataSets)
        mpLinechart!!.data = data
        mpLinechart!!.invalidate()
        val color: Int = requireContext().themeColor(R.attr.colorOnBackground)
        mpLinechart!!.axisLeft.textColor = color
        mpLinechart!!.axisRight.textColor = color
        mpLinechart!!.legend.textColor = color
        mpLinechart!!.lineData.setValueTextColor(color)
        mpLinechart!!.description.textColor = color
    }

    fun dataValues(): List<Entry> {
        val dataVals: MutableList<Entry> = ArrayList()
        dataVals.add(Entry(0f, 20f))
        dataVals.add(Entry(1f, 10f))
        dataVals.add(Entry(3f, 30f))
        dataVals.add(Entry(9f, 20f))
        dataVals.add(Entry(7f, 10f))
        return dataVals
    }


    private fun showToast(value: CharSequence) {
        Toast.makeText(this.context, value, Toast.LENGTH_LONG).show()
    }

}