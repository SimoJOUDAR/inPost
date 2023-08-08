package pl.inpost.recruitmenttask.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.inpost.recruitmenttask.databinding.ShipmentWrapperBinding
import pl.inpost.recruitmenttask.domain.model.ShipmentWrapper

class ShipmentWrapperAdapter(
    private val onItemClickListener: View.OnClickListener,
    private val onContextClickListener: View.OnContextClickListener
) : ListAdapter<ShipmentWrapper, ShipmentWrapperAdapter.WrapperViewHolder>(callback)  {

    companion object {
        private val callback = object : DiffUtil.ItemCallback<ShipmentWrapper>() {
            override fun areItemsTheSame(
                oldItem: ShipmentWrapper,
                newItem: ShipmentWrapper
            ): Boolean =
                oldItem.section == newItem.section

            override fun areContentsTheSame(
                oldItem: ShipmentWrapper,
                newItem: ShipmentWrapper
            ): Boolean =
                oldItem.section == newItem.section && oldItem.shipmentList == newItem.shipmentList
        }
    }

    inner class WrapperViewHolder(
        val binding: ShipmentWrapperBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrapperViewHolder {
        val binding = ShipmentWrapperBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WrapperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WrapperViewHolder, position: Int) {
        holder.binding.groupingSection.text = holder.itemView.context.getString(getItem(position).section.sectionRes)
        holder.binding.shipmentRecyclerview.apply {
            adapter = ShipmentItemAdapter(onItemClickListener, onContextClickListener)
            ((adapter) as ShipmentItemAdapter).submitList(currentList[position].shipmentList)
        }
    }

}