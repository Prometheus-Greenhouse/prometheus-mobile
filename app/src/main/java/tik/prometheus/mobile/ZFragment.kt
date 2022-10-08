package tik.prometheus.mobile

import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class ZFragment : Fragment() {
    protected fun showToast(value: CharSequence) {
        Toast.makeText(this.context, value, Toast.LENGTH_LONG).show()
    }
}