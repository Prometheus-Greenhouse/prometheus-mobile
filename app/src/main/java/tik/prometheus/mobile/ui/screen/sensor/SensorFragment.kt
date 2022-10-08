package tik.prometheus.mobile.ui.screen.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import tik.prometheus.mobile.NestableFragment
import tik.prometheus.mobile.R
import tik.prometheus.mobile.ZFragment
import tik.prometheus.mobile.databinding.FragmentHomeBinding
import tik.prometheus.mobile.ui.adapters.SensorAdapter
import tik.prometheus.mobile.utils.Utils
import tik.prometheus.mobile.utils.themeColor

class SensorFragment : ZFragment(), NestableFragment<SensorModel.SensorItem> {
    val TAG = SensorFragment::class.toString()

    private lateinit var viewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private var mpLinechart: LineChart? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        // CHAR
        initChart()

        // SENSOR
        val sensorAdapter = SensorAdapter(this)
        sensorAdapter.initAdapter(binding.sensorsRecyclerView, binding.btnRetry, viewModel.sensors, lifecycleScope)
        sensorAdapter.addLoadStateListener(binding.btnRetry, binding.progressBar)


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
        mpLinechart = binding.lineChart
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


    override fun insertNestedFragment(model: SensorModel.SensorItem) {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_container)
        val args = bundleOf(Utils.KEY_SENSOR_ID to model.sensor.id)
        navController.navigate(R.id.nav_sensor_detail, args)
//        val childFragment = SensorDetailFragment(model);
//        requireActivity().supportFragmentManager
//            .beginTransaction()
//            .replace(R.id.nav_host_container, childFragment)
//            .addToBackStack(SensorDetailFragment::class.java.name)
//            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}