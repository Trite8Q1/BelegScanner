package de.emgress.belegscanner

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddInvoiceItemFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddInvoiceItemFragment : BaseFragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_invoice_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val db = context?.let { Room.databaseBuilder(it, AppDatabase::class.java, "app_database").allowMainThreadQueries().build() }
        val btnSave: Button = requireView().findViewById(R.id.btnAddSave)
        val btnCancel: Button = requireView().findViewById(R.id.btnAddCancel)

        val name : EditText = requireView().findViewById(R.id.inputAddInvoiceName)
        val spinner: Spinner = requireView().findViewById(R.id.inputSpinner)
        val dateTime: RadioButton = requireView().findViewById(R.id.inputDateTime)
        val usage: EditText = requireView().findViewById(R.id.inputAddInvoiceUsage)
        val contributor: EditText = requireView().findViewById(R.id.inputAddContributor)
        val storageLocation:EditText = requireView().findViewById(R.id.inputAddStorageLocation)
        val optional: RadioButton = requireView().findViewById(R.id.inputAddOptional)

        context?.let { ArrayAdapter.createFromResource(it,R.array.invoice_types, android.R.layout.simple_spinner_item).also {
                adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        } }

        btnSave.setOnClickListener {
            val navController: NavController = findNavController()

            var optionalForEnterprise: Boolean? = false //Boolean von "optional" (RadioButton)
            var datetimeLong: Long? = null // Long von "dateTime" (RadioButton)

            if (dateTime.isChecked) {
                datetimeLong  = Date().time
            }
            else {
                Log.i("test", "datetime timestamp konnte nicht genommen werden")
            }

            if (optional.isChecked) {
                optionalForEnterprise = true
            }

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    var spinnerToString: Nothing? = null
                    (spinner.selectedItem to spinnerToString).toString()
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (spinner.selectedItem == "Receipt") {
                        spinner.selectedItem.toString()
                    }
                    if (spinner.selectedItem == "Document") {
                        spinner.selectedItem.toString()
                    }
                    if (spinner.selectedItem == "Digital") {
                        spinner.selectedItem.toString()
                    }
                }

            }
            //Required values to Insert: invoice Name, invoice Type(Spinner), datetime, storage location
            if (name.text.toString() == "" || datetimeLong == null || storageLocation.text.toString() == "")
            {
                Log.i("test", "Daten nicht gesetzt")
                Snackbar.make(view, "Invoice item need Requirements", Snackbar.LENGTH_LONG).show()
                alertDialogForInsert()
            }
            else {
                GlobalScope.launch {
                    val item = InvoiceModel(name.text.toString(), spinner.selectedItem.toString(), convertLongToTime(datetimeLong),usage.text.toString(), contributor.text.toString(), storageLocation.text.toString(), optionalForEnterprise)
                    val dao = context?.let { it1 -> AppDatabase.getDatabase(it1).getInvoiceDao() }
                    dao?.insertData(item)

                    GlobalScope.launch(Dispatchers.Main) {
                        navController.navigate(R.id.FirstFragment)
                        Snackbar.make(view, "Invoice item was added succesfully", Snackbar.LENGTH_LONG).show()
                    }
                }
            }
        }

        btnCancel.setOnClickListener {
            val navController: NavController = findNavController()
            navController.navigate(R.id.FirstFragment)
            Snackbar.make(view, "invoice item creation was canceled", Snackbar.LENGTH_LONG).show()
        }
    }

    private fun convertLongToTime(time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("dd.MM.yyyy HH:MM")
        return format.format(date)
    }

    private fun currentTimeToLong(): Long {
        return System.currentTimeMillis()
    }

    private fun convertDateToLong(date: String): Long {
        val df = SimpleDateFormat("dd.MM.yyyy HH:MM")
        return df.parse(date).time
    }

    private fun alertDialogForInsert() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("!!! ERROR !!!")
        builder.setMessage("The invoice name, invoice type, datetime and the storage location are required")
        builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
            dialogInterface.dismiss()
        }
        builder.show()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddInvoiceItemFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddInvoiceItemFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
