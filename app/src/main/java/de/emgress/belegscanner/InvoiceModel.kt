package de.emgress.belegscanner

data class InvoiceModel(val invoiceName : String, val invoiceType: String, val invoiceDate : String, val invoiceUsage : String, val invoiceContributor : String?, val invoiceStorageLocation : String?, val invoiceForEnterprise : Boolean?) {
    override fun toString(): String {
        return "invoiceName: $invoiceName | invoiceType: $invoiceType | invoiceDate: $invoiceDate | invoiceUsage: $invoiceUsage | invoiceContributor: $invoiceContributor " +
                "| invoiceStorageLocation: $invoiceStorageLocation | invoiceForEnterprise: $invoiceForEnterprise"
    }
}