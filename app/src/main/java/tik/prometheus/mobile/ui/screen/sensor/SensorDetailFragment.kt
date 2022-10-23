package tik.prometheus.mobile.ui.screen.sensor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import ir.mirrajabi.searchdialog.core.Searchable
import org.eclipse.paho.android.service.MqttAndroidClient
import tik.prometheus.mobile.ZViewModelFactory
import tik.prometheus.mobile.databinding.FragmentSensorDetailBinding
import tik.prometheus.mobile.services.MqttHelper
import tik.prometheus.mobile.services.MqttSensorViewHolder
import tik.prometheus.mobile.ui.screen.DoubleTapListener
import tik.prometheus.mobile.utils.EUnit
import tik.prometheus.mobile.utils.UnitModel
import tik.prometheus.mobile.utils.Utils
import tik.prometheus.rest.constants.SensorType
import tik.prometheus.rest.constants.SensorTypeModel

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
        _binding = FragmentSensorDetailBinding.inflate(inflater, container, false)

        val sensorId = requireArguments().getLong(Utils.KEY_SENSOR_ID)

        val v: SensorDetailViewModel by viewModels {
            ZViewModelFactory(mutableMapOf(Utils.KEY_SENSOR_ID to sensorId))
        }
        _sensorViewModel = v


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
        binding.llUnit.setOnClickListener(DoubleTapListener(requireContext()) {
            editUnit()
        })
        binding.llType.setOnClickListener(DoubleTapListener(requireContext()) {
            editType()
        })

        return binding.root
    }

    override fun updateMqttValue(value: String?) {
        binding.txtSensorValue.text = value
    }

    private fun editUnit() {
        val dialog = SimpleSearchDialogCompat(context, "Search", "Find unit", null, EUnit.getModels(),
            SearchResultListener { baseSearchDialogCompat: BaseSearchDialogCompat<Searchable>, searchable: Searchable, i: Int ->
                when (searchable) {
                    is UnitModel.UnitItem -> {
                        binding.txtUnitValue.text = searchable.unit.value
                        baseSearchDialogCompat.dismiss()
                    }

                    else -> {
                    }
                }
            }
        )
        dialog.show()
    }

    private fun editType() {
        val dialog = SimpleSearchDialogCompat(context, "Search", "Find sensor type", null, SensorType.getModels(),
            SearchResultListener { baseSearchDialogCompat: BaseSearchDialogCompat<Searchable>, searchable: Searchable, i: Int ->
                when (searchable) {
                    is SensorTypeModel-> {
                        binding.txtTypeValue.text = searchable.sensorType.value
                        baseSearchDialogCompat.dismiss()
                    }

                    else -> {
                    }
                }
            }
        )
        dialog.show()
    }
}