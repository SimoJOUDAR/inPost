package pl.inpost.recruitmenttask.data.persistence.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pl.inpost.recruitmenttask.data.persistence.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.persistence.entities.ShipmentEntity

@Database(
    entities = [
        ShipmentEntity::class
    ],
    version = 1
)
abstract class ShipmentsDatabase : RoomDatabase() {

    abstract fun shipmentDao(): ShipmentDao

    companion object {
        const val DATABASE_NAME = "shipment_database"

        @Volatile
        private var INSTANCE: ShipmentsDatabase? = null

        fun getDatabase(context: Context): ShipmentsDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        ShipmentsDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                    INSTANCE = instance
                    return instance
                }
        }
    }
}