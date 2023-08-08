package pl.inpost.recruitmenttask.data.network.api

import pl.inpost.recruitmenttask.domain.model.ShipmentNetwork

interface ShipmentApi {
    suspend fun getShipments(): List<ShipmentNetwork>
}
