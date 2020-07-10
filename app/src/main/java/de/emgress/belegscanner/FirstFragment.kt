package de.emgress.belegscanner

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.util.DBUtil
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.coroutines.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : BaseFragment() {
    private var items : ArrayList<InvoiceModel>? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Implementieren von Couroutine mit GlobalScope.launch {}
        GlobalScope.launch {
            val db = context?.let { it1 -> AppDatabase.getDatabase(it1).getInvoiceDao() }
            val data = db?.getAll()
            Log.i("test", "$data")

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
