package tik.prometheus.mobile.ui.screen.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import tik.prometheus.mobile.NestableFragment
import tik.prometheus.mobile.R
import tik.prometheus.mobile.ZFragment
import tik.prometheus.mobile.databinding.FragmentSensorBinding
import tik.prometheus.mobile.ui.adapters.SensorAdapter
import tik.prometheus.mobile.utils.Utils

class SensorFragment : ZFragment(), NestableFragment<SensorModel.SensorItem> {
    val TAG = SensorFragment::class.toString()

    private var _viewModel: SensorViewModel? = null
    private var _binding: FragmentSensorBinding? = null

    private val viewModel get() = _viewModel!!
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _viewModel = ViewModelProvider(this).get(SensorViewModel::class.java)
        _binding = FragmentSensorBinding.inflate(layoutInflater, container, false)

        // SENSOR
        val sensorAdapter = SensorAdapter(this)
        sensorAdapter.initAdapter(binding.sensorsRecyclerView, binding.btnRetry, viewModel.sensors, lifecycleScope)
        sensorAdapter.addLoadStateListener(binding.btnRetry, binding.progressBar)


        return binding.root
    }
    override fun insertNestedFragment(model: SensorModel.SensorItem) {
        val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_container)
        val pair = Pair(Utils.KEY_SENSOR_ID, model.sensor.id)
        val args = bundleOf(pair)
        navController.navigate(R.id.nav_sensor_detail, args)
    }
}