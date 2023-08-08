package pl.inpost.recruitmenttask.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.databinding.ShipmentItemBinding
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork

class ShipmentsAdapter (
    private val onItemClickListener: View.OnClickListener,
    private val onContextClickListener: View.OnContextClickListener
) : ListAdapter<ShipmentNetwork, ShipmentsAdapter.ShipmentViewHolder>(callback) {

    companion object {
        private val callback = object : DiffUtil.ItemCallback<ShipmentNetwork>() {
            override fun areItemsTheSame(oldItem: ShipmentNetwork, newItem: ShipmentNetwork): Boolean =
                oldItem.number == newItem.number

            override fun areContentsTheSame(oldItem: ShipmentNetwork, newItem: ShipmentNetwork): Boolean =
                oldItem.number == newItem.number
        }
    }

    class ShipmentViewHolder(val binding: ShipmentItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipmentViewHolder {
        val binding = ShipmentItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShipmentViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShipmentViewHolder, position: Int) {
        holder.binding.shipment = getItem(position)
        with(holder.binding.moreSection) {
            tag = getItem(position)
            setOnClickListener(onItemClickListener)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                setOnContextClickListener(onContextClickListener)
            }
        }
    }
}