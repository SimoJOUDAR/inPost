package pl.inpost.recruitmenttask.util

import pl.inpost.recruitmenttask.domain.model.ShipmentNetwork
import pl.inpost.recruitmenttask.domain.model.ShipmentWrapper


fun List<ShipmentNetwork>.wrapShipments() : List<ShipmentWrapper> {
    val highlighted = mutableListOf<ShipmentNetwork>()
    val others = mutableListOf<ShipmentNetwork>()

    for (shipment in this) {
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