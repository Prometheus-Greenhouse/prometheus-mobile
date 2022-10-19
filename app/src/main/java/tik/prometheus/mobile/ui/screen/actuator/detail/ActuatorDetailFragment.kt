package tik.prometheus.mobile.ui.screen.actuator.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import tik.prometheus.mobile.ZFragment
import tik.prometheus.mobile.databinding.FragmentActuatorDetailBinding
import tik.prometheus.mobile.utils.Utils

class ActuatorDetailFragment : ZFragment() {
    private val viewModel: ActuatorDetailViewModel by viewModels { ActuatorDetailViewModel.Factory(zContainer.actuatorRepository) }
    private var _binding: FragmentActuatorDetailBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentActuatorDetailBinding.inflate(inflater, container, false)
        val actuatorId = requireArguments().getLong(Utils.KEY_ACTUATOR_ID)
        println(actuatorId)
        viewModel.getActuatorDetail(actuatorId)
        viewModel.loadState.observe(requireActivity(), Observer {
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
        viewModel.actuator.observe(requireActivity(), Observer {
            binding.txtId.text = it.id.toString()
            binding.txtLocalId.text = it.localId
            binding.txtTypeId.text = it.type?.value
            binding.txtLabel.text = it.label
            binding.txtNorth.text = it.north.toString()
            binding.txtWest.text = it.west.toString()

        })

        return binding.root
    }
}