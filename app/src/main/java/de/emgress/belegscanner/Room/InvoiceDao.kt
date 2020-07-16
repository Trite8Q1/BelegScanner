package de.emgress.belegscanner.Room

import androidx.room.*
import de.emgress.belegscanner.Models.InvoiceModel

@Dao
interface InvoiceDao {
    @Query("SELECT * FROM InvoiceModel")
    suspend fun getAll(): List<InvoiceModel>

    //TODO: Query ertesllen mit, womit ich ein einzelnes InvoiceObjekt(keine Liste) bekomme mit Paramter ID arbeiten!
    @Query("SELECT * FROM InvoiceModel WHERE id IN (:invoiceID)")
    suspend fun getID(invoiceID:Long): InvoiceModel

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertData(data: InvoiceModel)

    @Update(onConflict = OnConflictStrategy.ABORT)
    suspend fun updateData(data: InvoiceModel)
}