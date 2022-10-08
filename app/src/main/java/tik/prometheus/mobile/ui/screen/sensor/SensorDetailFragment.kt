package tik.prometheus.mobile.ui.screen.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import org.eclipse.paho.android.service.MqttAndroidClient
import tik.prometheus.mobile.ZViewModelFactory
import tik.prometheus.mobile.databinding.FragmentSensorDetailBinding
import tik.prometheus.mobile.services.MqttHelper
import tik.prometheus.mobile.services.MqttSensorViewHolder
import tik.prometheus.mobile.utils.Utils

class SensorDetailFragment : Fragment(), MqttSensorViewHolder {
    private var _sensorViewModel: SensorDetailViewModel? = null
    private val sensorViewModel get() = _sensorViewModel!!
    private var _binding: FragmentSensorDetailBinding? = null
    private val binding get() = _binding!!
    private var mqttAndroidClient: MqttAndroidClient? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _sensorViewModel = ViewModelProvider(this).get(SensorDetailViewModel::class.java)
        val sensorId = requireArguments().getLong(Utils.KEY_SENSOR_ID)

        val v: SensorDetailViewModel by viewModels {
            ZViewModelFactory(mutableMapOf(Utils.KEY_SENSOR_ID to sensorId))
        }
        _sensorViewModel = v

        _binding = FragmentSensorDetailBinding.inflate(inflater, container, false)

        sensorViewModel.sensor.observe(requireActivity(), Observer {
            mqttAndroidClient = MqttHelper.createSensorListener(binding.root.context, it.topic, this)
            binding.txtSensorUnit.text = it.unit
            // TODO:
            //  binding.txtFarmValue.text = sensorItem.sensor
            //  binding.txtGreenhouseValue.text
            binding.txtIdValue.text = it.id.toString()
            binding.txtLocalIdValue.text = it.localId
            binding.txtTypeValue.text = it.type
            binding.txtUnitValue.text = it.unit
        })

        return binding.root
    }

    override fun updateMqttValue(value: String?) {
        binding.txtSensorValue.text = value
    }
}