package tik.prometheus.mobile.ui.screen.greenhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import tik.prometheus.mobile.NestableFragment
import tik.prometheus.mobile.R
import tik.prometheus.mobile.databinding.FragmentGreenhouseBinding
import tik.prometheus.mobile.ui.adapters.GreenhouseAdapter

class GreenhouseFragment : Fragment(), NestableFragment<GreenhouseModel.GreenhouseItem> {
    private val TAG = GreenhouseFragment::class.toString()

    private lateinit var viewModel: GreenhouseViewModel
    private var _binding: FragmentGreenhouseBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(GreenhouseViewModel::class.java);
        _binding = FragmentGreenhouseBinding.inflate(layoutInflater)
        val greenhouseAdapter = GreenhouseAdapter(this)
        greenhouseAdapter.initAdapter(binding.rvGreenhouse, binding.btnRetry, viewModel.greenhouses, lifecycleScope)
        greenhouseAdapter.addLoadStateListener(binding.btnRetry, binding.progressBar)

        return binding.root
    }

    override fun insertNestedFragment(model: GreenhouseModel.GreenhouseItem) {
        val childFragment = GreenhouseDetailFragment(model);
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_container, childFragment)
            .addToBackStack(GreenhouseDetailFragment::class.java.name)
            .commit()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}