package tik.prometheus.mobile.ui.screen.greenhouse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import tik.prometheus.mobile.NestableFragment
import tik.prometheus.mobile.databinding.FragmentGreenhouseBinding
import tik.prometheus.mobile.ui.adapters.GreenhouseAdapter
import tik.prometheus.mobile.ui.adapters.ZLoadStateAdapter

class GreenhouseFragment : Fragment(), NestableFragment<GreenhouseModel.GreenhouseItem> {
    private val TAG = GreenhouseFragment::class.toString()

    private lateinit var viewModel: GreenhouseViewModel
    private lateinit var binding: FragmentGreenhouseBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewModel = ViewModelProvider(this).get(GreenhouseViewModel::class.java);
        binding = FragmentGreenhouseBinding.inflate(layoutInflater)
        val greenhouseAdapter = GreenhouseAdapter(this)
        greenhouseAdapter.initAdapter(binding.rvGreenhouse, binding.btnRetry, viewModel.greenhouses, lifecycleScope)
        greenhouseAdapter.addLoadStateListener(binding.btnRetry, binding.progressBar)

        return binding.root
    }

    override fun insertNestedFragment(model: GreenhouseModel.GreenhouseItem) {
    }

}