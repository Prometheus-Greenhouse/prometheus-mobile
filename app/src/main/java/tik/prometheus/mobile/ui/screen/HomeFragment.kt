package tik.prometheus.mobile.ui.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import ir.mirrajabi.searchdialog.core.Searchable
import tik.prometheus.mobile.NestableFragment
import tik.prometheus.mobile.R
import tik.prometheus.mobile.ZFragment
import tik.prometheus.mobile.databinding.FragmentHomeBinding
import tik.prometheus.mobile.ui.adapters.SensorAdapter
import tik.prometheus.mobile.ui.screen.sensor.SensorModel
import tik.prometheus.mobile.utils.Utils
import tik.prometheus.mobile.utils.themeColor

class HomeFragment : ZFragment(), NestableFragment<SensorModel.SensorItem> {
    val TAG = HomeFragment::class.toString()

    private val viewModel: HomeViewModel by viewModels { HomeViewModel.Factory(zContainer.sensorRepository) }
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    private val sensorAdapter = SensorAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        viewModel.loadSensors()
        initAdapter()

        viewModel.greenhouseId.observe(requireActivity()) {
            viewModel.loadSensors()
            initAdapter()
        }
        viewModel.selectSensors.observe(requireActivity()) {
            binding.txtSelectSensorCount.text = it?.size.toString()
            viewModel.loadSensorLineChartData(
                arrayListOf<Int>(
                    requireContext().themeColor(R.color.cyan_200),
                    requireContext().themeColor(R.color.cyan_500),
                    requireContext().themeColor(R.color.cyan_700),
                    requireContext().themeColor(R.color.cyan_900),
                )
            )
        }
        binding.txtFromValue.text = viewModel.fromDate.value.toString()
        binding.txtToValue.text = viewModel.toDate.value.toString()

        binding.clSelectedSensorCount.setOnClickListener(View.OnClickListener {
            val dialog = SimpleSearchDialogCompat(context, "Search", "Find sensor type", null, viewModel.selectSensors.value?.values?.toCollection(ArrayList()),
                SearchResultListener { baseSearchDialogCompat: BaseSearchDialogCompat<Searchable>, searchable: Searchable, i: Int ->
                    when (searchable) {
                        is SensorModel.SensorItem -> {
                            viewModel.selectSensors.value?.remove(searchable.sensor.id)
                            viewModel.selectSensors.postValue(viewModel.selectSensors.value)
                        }

                        else -> {
                        }
                    }
                    baseSearchDialogCompat.dismiss()
                }
            )
            dialog.show()
        })

        // CHAR
        initChart()
        viewModel.sensorLineChartData.observe(requireActivity()) {
            val data = LineData(it)
            binding.lineChart.data = data
            binding.lineChart.invalidate()
            val color: Int = requireContext().themeColor(R.attr.colorOnBackground)
            binding.lineChart.axisLeft.textColor = color
            binding.lineChart.axisRight.textColor = color
            binding.lineChart.legend.textColor = color
            binding.lineChart.lineData.setValueTextColor(color)
            binding.lineChart.description.textColor = color
            binding.lineChart.description.text = ""
        }


        return binding.root
    }

    private fun initAdapter() {
        sensorAdapter.initAdapter(binding.sensorLayout.sensorsRecyclerView, binding.sensorLayout.btnRetry, viewModel.sensors, lifecycleScope)
        sensorAdapter.addLoadStateListener(binding.sensorLayout.btnRetry, binding.sensorLayout.progressBar) { showToast(it.error.toString()) }
        sensorAdapter.onLongClickListenerFactory = OnLongClickSensorItemListenerFactory()
    }

    inner class OnLongClickSensorItemListenerFactory : SensorAdapter.OnLongClickListenerFactory {
        @SuppressLint("ClickableViewAccessibility")
        override fun create(viewHolder: SensorAdapter.SensorViewHolder, sensorModel: SensorModel.SensorItem): View.OnLongClickListener {
            return View.OnLongClickListener { v ->
                if (viewModel.selectSensors.value?.containsKey(sensorModel.sensor.id) == false) {
                    viewModel.selectSensors.value?.put(sensorModel.sensor.id, sensorModel)
                    viewModel.selectSensors.postValue(viewModel.selectSensors.value)
                } else {
                    viewModel.selectSensors.value?.remove(sensorModel.sensor.id)
                    viewModel.selectSensors.postValue(viewModel.selectSensors.value)
                }

                true
            }
        }

    }


    private fun initChart() {
        val mpLinechart = binding.lineChart
        val dataSets: MutableList<ILineDataSet> = ArrayList()
        dataSets.add(
            LineDataSet(dataValues(), "Data set 1")
        )
        val data = LineData(dataSets)
        mpLinechart.data = data
        mpLinechart.invalidate()

        val color: Int = requireContext().themeColor(R.attr.colorOnBackground)
        mpLinechart.axisLeft.textColor = color
        mpLinechart.axisRight.textColor = color
        mpLinechart.legend.textColor = color
        mpLinechart.lineData.setValueTextColor(color)
        mpLinechart.description.textColor = color
        mpLinechart.description.text = ""

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
        val pair = Pair(Utils.KEY_SENSOR_ID, model.sensor.id)
        val args = bundleOf(pair)
        navController.navigate(R.id.nav_sensor_detail, args)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
}