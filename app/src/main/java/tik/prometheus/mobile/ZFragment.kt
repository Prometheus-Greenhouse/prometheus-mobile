package tik.prometheus.mobile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class ZFragment : Fragment() {
    protected fun showToast(value: CharSequence) {
        Toast.makeText(context, value, Toast.LENGTH_LONG).show()
    }

    lateinit var zContainer: ZContainer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        zContainer = if (activity != null) (requireActivity().application as ZApplication).container else ZContainer()
    }
}