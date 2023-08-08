package pl.inpost.recruitmenttask.presentation.shipmentList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.domain.model.ShipmentNetwork
import pl.inpost.recruitmenttask.domain.model.ShipmentWrapper
import pl.inpost.recruitmenttask.util.setState
import javax.inject.Inject

@HiltViewModel
class ShipmentListViewModel @Inject constructor(
    private val shipmentApi: ShipmentApi
) : ViewModel() {

    //TODO: use coroutines

    private val mutableViewState = MutableLiveData<List<ShipmentWrapper>>(emptyList())
    val viewState: LiveData<List<ShipmentWrapper>> = mutableViewState

    init {
        refreshData()
    }

    fun refreshData() {
        GlobalScope.launch(Dispatchers.Main) {
            val shipments = shipmentApi.getShipments()
            val wrappers = wrapShipments(shipments)
            mutableViewState.setState { wrappers }

        }
    }

    private fun wrapShipments(shipments: List<ShipmentNetwork>) : List<ShipmentWrapper> {
        val highlighted = mutableListOf<ShipmentNetwork>()
        val others = mutableListOf<ShipmentNetwork>()

        for (shipment in shipments) {
            if (shipment.operations.highlight) highlighted.add(shipment)
            else others.add(shipment)
        }
        val wrapperList = mutableListOf<ShipmentWrapper>()

        if (highlighted.isNotEmpty())
            wrapperList.add(ShipmentWrapper(ShipmentWrapper.Section.HIGHLIGHTED, highlighted))
        if (others.isNotEmpty())
            wrapperList.add(ShipmentWrapper(ShipmentWrapper.Section.OTHER, others))

        return wrapperList
    }
}
