package pl.inpost.recruitmenttask.data.persistence.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.inpost.recruitmenttask.data.persistence.entities.ShipmentEntity

@Dao
interface ShipmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShipment(agent: ShipmentEntity)

    @Query("SELECT * FROM shipment")
    fun getAllShipments(): Flow<List<ShipmentEntity>>

    @Query("SELECT * FROM shipment WHERE number = :number")
    fun getShipmentById(number: String): Flow<ShipmentEntity>

    @Delete
    fun deleteShipmentById(shipment: ShipmentEntity)

}