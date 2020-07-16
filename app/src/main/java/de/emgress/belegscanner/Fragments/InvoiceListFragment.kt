package de.emgress.belegscanner.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.navigation.findNavController
import de.emgress.belegscanner.Room.AppDatabase
import de.emgress.belegscanner.Adapter.InvoiceListAdapter
import de.emgress.belegscanner.R
import kotlinx.coroutines.*

class InvoiceListFragment : BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_invoice_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val invoiceList = requireView().findViewById<ListView>(R.id.invoiceList)

        invoiceList.setOnItemClickListener { parent, view, position, id ->
            val action = InvoiceListFragmentDirections.actionFirstFragmentToDetailFragment(id)
            view.findNavController().navigate(action)
        }

        GlobalScope.launch {
            val dao = context?.let { it1 -> AppDatabase.getDatabase(
                it1
            ).getInvoiceDao() }
            val data = dao?.getAll()


            GlobalScope.launch(Dispatchers.Main) {
                if (data != null) {
                    val adapter = context?.let {
                        InvoiceListAdapter(
                            it,
                            data
                        )
                    }
                    invoiceList.adapter = adapter
                }
                else {
                    return@launch
                }
            }
        }
    }
}
