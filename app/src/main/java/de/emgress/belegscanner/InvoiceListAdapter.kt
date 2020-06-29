package de.emgress.belegscanner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class InvoiceListAdapter(context : Context, items : ArrayList<InvoiceModel>) : BaseAdapter() {
    public val context : Context
    public val items : ArrayList<InvoiceModel>

    init {
        this.context = context
        this.items = items
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = LayoutInflater.from(context)
        val listEntry = inflater.inflate(R.layout.adapter_invoice_list, parent, false)
        val tvName = listEntry.findViewById<TextView>(R.id.tvInvoiceName)
        val tvDate = listEntry.findViewById<TextView>(R.id.tvInvoiceDate)

        tvName.text = items[position].invoiceName
        tvDate.text = items[position].invoiceDate

        return listEntry
    }

    override fun getItem(position: Int): InvoiceModel {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }
}