package pl.inpost.recruitmenttask.data.persistence.mappers

import pl.inpost.recruitmenttask.data.persistence.entities.ShipmentEntity
import pl.inpost.recruitmenttask.domain.model.OperationsNetwork
import pl.inpost.recruitmenttask.domain.model.ShipmentNetwork

fun ShipmentNetwork.toShipmentEntity() =
    ShipmentEntity(
        number = this.number,
        shipmentType = this.shipmentType,
        status = this.status
    )

fun ShipmentEntity.toShipmentNetwork() =
    ShipmentNetwork(
        number = this.number,
        shipmentType = this.shipmentType,
        status = this.status,
        eventLog = listOf(),
        openCode = null,
        expiryDate = null,
        storedDate = null,
        pickUpDate = null,
        receiver = null,
        sender = null,
        operations = OperationsNetwork(
            manualArchive = false,
            delete = false,
            collect = false,
            highlight = false,
            expandAvizo = false,
            endOfWeekCollection = false
        )
    )