package de.emgress.belegscanner

import androidx.room.*

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM InvoiceModel")
    suspend fun getAll(): List<InvoiceModel>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertData(data: InvoiceModel)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateData(data: InvoiceModel)
}