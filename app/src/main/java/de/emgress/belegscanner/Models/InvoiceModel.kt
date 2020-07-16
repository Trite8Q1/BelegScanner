package de.emgress.belegscanner.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class InvoiceModel(
    @ColumnInfo(name = "invoice_name") var invoiceName: String,
    @ColumnInfo(name = "invoice_type") var invoiceType: String?,
    @ColumnInfo(name = "invoice_date") var invoiceDate: String,
    @ColumnInfo(name = "invoice_usage") var invoiceUsage: String?,
    @ColumnInfo(name = "invoice_contributor") var invoiceContributor: String?,
    @ColumnInfo(name = "invoice_storage_location") var invoiceStorageLocation: String,
    @ColumnInfo(name = "invoice_for_enterprise") var invoiceForEnterprise: Boolean?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}