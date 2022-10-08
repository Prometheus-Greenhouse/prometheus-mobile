package tik.prometheus.mobile.ui.screen.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import org.eclipse.paho.android.service.MqttAndroidClient
import tik.prometheus.mobile.databinding.FragmentSensorDetailBinding
import tik.prometheus.mobile.services.MqttHelper
import tik.prometheus.mobile.services.MqttSensorViewHolder
import tik.prometheus.mobile.ui.screen.home.SensorModel

class SensorDetailFragment(private val sensorItem: SensorModel.SensorItem) : Fragment(), MqttSensorViewHolder {
    private var sensorViewModel: SensorDetailViewModel? = null
    private var _binding: FragmentSensorDetailBinding? = null
    private val binding get() = _binding!!
    private var mqttAndroidClient: MqttAndroidClient? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        sensorViewModel = ViewModelProvider(this).get(SensorDetailViewModel::class.java)
        _binding = FragmentSensorDetailBinding.inflate(inflater, container, false)
        mqttAndroidClient = MqttHelper.createSensorListener(binding.root.context, sensorItem.sensor.topic, this)
        val sensor = sensorItem.sensor

        binding.txtSensorUnit.text = sensor.unit
        // TODO:
        //  binding.txtFarmValue.text = sensorItem.sensor
        //  binding.txtGreenhouseValue.text
        binding.txtIdValue.text = sensor.id.toString()
        binding.txtLocalIdValue.text = sensor.localId
        binding.txtTypeValue.text = sensor.type
        binding.txtUnitValue.text = sensor.unit

        return binding.root
    }

    override fun updateMqttValue(value: String?) {
        binding.txtSensorValue.text = value
    }
}