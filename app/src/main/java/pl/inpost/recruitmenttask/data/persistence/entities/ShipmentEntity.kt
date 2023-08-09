package pl.inpost.recruitmenttask.data.persistence.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "shipment"
)
data class ShipmentEntity(
    @PrimaryKey(autoGenerate = false)
    @NonNull val number: String,
    val shipmentType: String,
    val status: String
)