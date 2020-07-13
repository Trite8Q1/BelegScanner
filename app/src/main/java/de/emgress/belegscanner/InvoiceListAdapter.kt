package de.emgress.belegscanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class InvoiceListAdapter(val context : Context, val data : List<InvoiceModel>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val listEntry = inflater.inflate(R.layout.adapter_invoice_list, parent, false)
        val tvName = listEntry.findViewById<TextView>(R.id.tvInvoiceName)
        val tvDate = listEntry.findViewById<TextView>(R.id.tvInvoiceDate)

        tvName.text = data[position].invoiceName.toString()
        tvDate.text = data[position].invoiceDate.toString()

        return listEntry
    }

    override fun getItem(position: Int): InvoiceModel {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return data[position].id
    }

    override fun getCount(): Int {
        return data.size
    }
}