package pl.inpost.recruitmenttask.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.inpost.recruitmenttask.data.persistence.dao.ShipmentDao
import pl.inpost.recruitmenttask.data.persistence.database.ShipmentsDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PersistenceModule {

    companion object {

        @Singleton
        @Provides
        fun provideShipmentDatabase(@ApplicationContext context: Context): ShipmentsDatabase {
            return Room.databaseBuilder(
                context,
                ShipmentsDatabase::class.java,
                ShipmentsDatabase.DATABASE_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        @Singleton
        @Provides
        fun provideShipmentDao(db: ShipmentsDatabase): ShipmentDao {
            return db.shipmentDao()
        }
    }
}