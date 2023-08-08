package pl.inpost.recruitmenttask.data.repositories

import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import javax.inject.Inject

class ShipmentsRepository @Inject constructor(
    private val shipmentApi: ShipmentApi
) {
    suspend fun getShipmentsFromApi() = shipmentApi.getShipments()
}