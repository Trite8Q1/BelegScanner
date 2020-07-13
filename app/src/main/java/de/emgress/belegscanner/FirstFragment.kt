package de.emgress.belegscanner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.fragment.app.findFragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.adapter_invoice_list.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.*

class FirstFragment : BaseFragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val invoiceList = requireView().findViewById<ListView>(R.id.invoiceList)

        invoiceList.setOnItemClickListener { parent, view, position, id ->
            val action = FirstFragmentDirections.actionFirstFragmentToDetailFragment(id)
            //val navController = findNavController()
            view.findNavController().navigate(action)
        }

        GlobalScope.launch {
            val dao = context?.let { it1 -> AppDatabase.getDatabase(it1).getInvoiceDao() }
            val data = dao?.getAll()


            GlobalScope.launch(Dispatchers.Main) {
                if (data != null) {
                    val adapter = context?.let { InvoiceListAdapter(it, data) }
                    invoiceList.adapter = adapter
                }
                else {
                    return@launch
                }
            }
        }
    }
}
