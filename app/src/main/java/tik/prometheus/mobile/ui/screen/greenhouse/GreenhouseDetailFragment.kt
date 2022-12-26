package tik.prometheus.mobile.ui.screen.greenhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.paging.LoadState
import tik.prometheus.mobile.R
import tik.prometheus.mobile.ZFragment
import tik.prometheus.mobile.databinding.FragmentGreenhouseDetailBinding
import tik.prometheus.mobile.utils.Utils

class GreenhouseDetailFragment : ZFragment() {
    val TAG = GreenhouseDetailFragment::class.toString()
    val viewModel: GreenhouseDetailViewModel by viewModels { GreenhouseDetailViewModel.Factory(zContainer.greenhouseRepository) }
    private var _binding: FragmentGreenhouseDetailBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentGreenhouseDetailBinding.inflate(inflater, container, false)

        val greenhouseId = requireArguments().getLong(Utils.KEY_GREENHOUSE_ID)

        viewModel.getGreenhouseDetail(greenhouseId)
        viewModel.loadState.observe(requireActivity(), Observer {
            println("observer load state")
            when (it) {
                is LoadState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.GONE
                    binding.btnSave.visibility = View.GONE
                }

                is LoadState.Error -> {
                    binding.btnRetry.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE
                    binding.btnSave.visibility = View.GONE
                }

                is LoadState.NotLoading -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSave.visibility = View.VISIBLE
                    binding.btnRetry.visibility = View.GONE
                }
            }
        })

        viewModel.greenhouse.observe(requireActivity(), Observer {
            println("observer load gh")
            binding.txtId.text = it.id.toString()
            binding.txtFarm.text = it.farmId.toString()
            binding.txtType.text = it.type
            binding.txtLabel.text = it.label
            binding.txtArea.text = it.area.toString()
            binding.txtCultivationArea.text = it.cultivationArea.toString()
            binding.txtHeight.text = it.height.toString()
            binding.txtWidth.text = it.width.toString()
            binding.txtLength.text = it.length.toString()
            binding.txtActuatorCount.text = "Actuator (" + it.actuators.size + ")"
            binding.txtSensorCount.text = "Sensor (" + it.sensors.size + ")"
        })

        binding.btnActuatorCount.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_container);
            val args = bundleOf(Utils.KEY_GREENHOUSE_ID to viewModel.greenhouse.value?.id)
            navController.navigate(R.id.nav_actuator, args);
        }

        binding.btnSensorCount.setOnClickListener {
            val navController = Navigation.findNavController(requireActivity(), R.id.nav_host_container);
            val args = bundleOf(Utils.KEY_GREENHOUSE_ID to viewModel.greenhouse.value?.id)
            navController.navigate(R.id.nav_sensor, args);
        }

        return binding.root;
    }

//    fun loadData() {
//        binding.progressBar.visibility = View.VISIBLE
//        binding.btnRetry.visibility = View.GONE
//        binding.btnSave.visibility = View.GONE
//
//        GlobalScope.launch {
//            val res = viewModel.getGreenhouseDetail(greenhouseItem.greenhouse.id)
//            if (res.isSuccessful) {
//                binding.progressBar.visibility = View.GONE
//                binding.btnRetry.visibility = View.GONE
//                binding.btnSave.visibility = View.VISIBLE
//                mapData(res.body()!!)
//            } else {
//                binding.progressBar.visibility = View.GONE
//                binding.btnRetry.visibility = View.VISIBLE
//                binding.btnSave.visibility = View.GONE
//            }
//        }
//    }
//
//    fun mapData(greenhouse: Greenhouse) {
//        binding.txtId.text = greenhouse.id.toString()
//        binding.txtFarm.text = greenhouse.farmId.toString()
//        binding.txtType.text = greenhouse.type
//        // TODO
//        // binding.txtTitle = greenhouse.title
//        binding.txtArea.text = greenhouse.area.toString()
//        binding.txtCultivationArea.text = greenhouse.cultivationArea.toString()
//        binding.txtHeight.text = greenhouse.height.toString()
//        binding.txtWidth.text = greenhouse.width.toString()
//        binding.txtLength.text = greenhouse.length.toString()
//    }


}