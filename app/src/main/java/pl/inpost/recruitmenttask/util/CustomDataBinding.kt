package pl.inpost.recruitmenttask.util

import android.opengl.Visibility
import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import pl.inpost.recruitmenttask.R
import pl.inpost.recruitmenttask.network.model.ShipmentNetwork
import pl.inpost.recruitmenttask.network.model.ShipmentStatus

class CustomDataBinding {

    companion object {

        @JvmStatic
        @BindingAdapter("status")
        fun bindStatus(textView: TextView, shipment: ShipmentNetwork?) {
            shipment?.let {
                val status = ShipmentStatus.values().find { shipmentStatus ->
                    shipmentStatus.name == it.status
                }?.nameRes ?: ShipmentStatus.NA.nameRes
                textView.text = textView.context.getString(status)
            }
        }

        @JvmStatic
        @BindingAdapter("sender")
        fun bindSender(textView: TextView, shipment: ShipmentNetwork?) {
            shipment?.let {
                textView.text =
                    if (it.operations.highlight)
                        it.sender?.email ?: NA
                    else
                        it.sender?.name ?: NA
            }
        }

        @JvmStatic
        @BindingAdapter("date_title")
        fun bindDateTitle(textView: TextView, shipment: ShipmentNetwork?) {
            shipment?.let {
                when (it.status) {
                    ShipmentStatus.DELIVERED.name -> textView.text = textView.context.getString(R.string.pickup_deadline)
                    RECEIVED -> textView.text = textView.context.getString(R.string.received)
                    else -> textView.visibility = View.GONE
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        @JvmStatic
        @BindingAdapter("date")
        fun bindDate(textView: TextView, shipment: ShipmentNetwork?) {
            shipment?.let {
                when (it.status) {
                    READY_TO_PICKUP -> textView.text = it.expiryDate?.setFormattedDate()
                    RECEIVED -> textView.text = it.expiryDate?.setFormattedDate()
                    else -> textView.visibility = View.GONE
                }
            }
        }

        private const val READY_TO_PICKUP = "READY_TO_PICKUP"
        private const val RECEIVED = "RECEIVED"
        private const val NA = "na"

    }
}