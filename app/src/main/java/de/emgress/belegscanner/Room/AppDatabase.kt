package de.emgress.belegscanner.Room

import android.content.Context
import androidx.room.*
import de.emgress.belegscanner.Models.InvoiceModel

@Database(entities = [InvoiceModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getInvoiceDao(): InvoiceDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context) : AppDatabase {
            val tmpInstance =
                INSTANCE

            if (tmpInstance != null) {
                return tmpInstance
            }

            synchronized(this) {
                val instance  = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app_database").build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
