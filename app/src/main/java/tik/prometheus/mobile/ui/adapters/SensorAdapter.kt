package tik.prometheus.mobile.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tik.prometheus.mobile.R
import tik.prometheus.mobile.databinding.ItemPagingSensorBinding
import tik.prometheus.mobile.databinding.ItemPagingSeperatorBinding
import tik.prometheus.mobile.services.MqttHelper
import tik.prometheus.mobile.ui.screen.home.SensorModel

class SensorAdapter : PagingDataAdapter<SensorModel, RecyclerView.ViewHolder>(SensorComparator) {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val sensorModel = getItem(position)
        sensorModel.let {
            when (sensorModel) {
                is SensorModel.SensorItem -> {
                    val viewHolder = holder as SensorViewHolder
                    sensorModel.mqttClient = MqttHelper.createSensorListener(viewHolder.sensorItemBinding.root.context, sensorModel.sensor.topic, viewHolder);
                    viewHolder.sensorItemBinding.unit.text = sensorModel.sensor.unit
                    viewHolder.sensorItemBinding.value.text = sensorModel.sensor.topic
                }

                is SensorModel.SeparatorItem -> {
                    val viewHolder = holder as SensorSeparatorViewHolder
                    viewHolder.movieItemSeperatorBinding.separatorDescription.text = sensorModel.description
                }

                else -> {}
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is SensorModel.SensorItem -> R.layout.item_paging_sensor
            is SensorModel.SeparatorItem -> R.layout.item_paging_seperator
            else -> throw UnsupportedOperationException("Unknown view")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_paging_sensor -> {
                SensorViewHolder(
                    ItemPagingSensorBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }

            else -> {
                SensorSeparatorViewHolder(
                    ItemPagingSeperatorBinding
                        .inflate(LayoutInflater.from(parent.context), parent, false)
                )
            }
        }
    }

    class SensorViewHolder(val sensorItemBinding: ItemPagingSensorBinding) : RecyclerView.ViewHolder(sensorItemBinding.root)

    class SensorSeparatorViewHolder(val movieItemSeperatorBinding: ItemPagingSeperatorBinding) : RecyclerView.ViewHolder(movieItemSeperatorBinding.root)

    object SensorComparator : DiffUtil.ItemCallback<SensorModel>() {
        override fun areItemsTheSame(oldItem: SensorModel, newItem: SensorModel): Boolean {
            return (oldItem is SensorModel.SensorItem && newItem is SensorModel.SensorItem &&
                    oldItem.sensor.id == newItem.sensor.id)
        }

        override fun areContentsTheSame(oldItem: SensorModel, newItem: SensorModel): Boolean =
            oldItem == newItem

    }


}