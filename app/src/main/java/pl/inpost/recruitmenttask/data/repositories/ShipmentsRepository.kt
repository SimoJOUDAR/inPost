package pl.inpost.recruitmenttask.data.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import pl.inpost.recruitmenttask.data.network.api.ShipmentApi
import pl.inpost.recruitmenttask.data.persistence.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.persistence.mappers.toShipmentEntity
import pl.inpost.recruitmenttask.data.persistence.mappers.toShipmentNetwork
import pl.inpost.recruitmenttask.domain.model.ShipmentNetwork
import javax.inject.Inject

class ShipmentsRepository @Inject constructor(
    private val shipmentApi: ShipmentApi,
    private val shipmentDao: ShipmentDao
) {
    suspend fun getShipmentsFromApi() = shipmentApi.getShipments()

    fun getArchivedShipmentsFromDatabase(): Flow<List<ShipmentNetwork>> {
        return shipmentDao.getAllShipments()
            .distinctUntilChanged()
            .map { entityList ->
                entityList.map { shipmentList ->
                    shipmentList.toShipmentNetwork()
                }
            }
    }

    suspend fun saveShipmentAsArchive(shipment: ShipmentNetwork) {
        return shipmentDao.insertShipment(shipment.toShipmentEntity())
    }
}