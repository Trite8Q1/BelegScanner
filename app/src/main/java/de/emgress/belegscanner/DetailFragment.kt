package de.emgress.belegscanner

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.navArgs
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.zip.Inflater
import kotlin.properties.Delegates


class DetailFragment: BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflater = LayoutInflater.from(context)
        val inflation = inflater.inflate(R.layout.fragment_add_invoice_item, container, false)
        val btnEdit: Button = inflation.findViewById(R.id.btnAddSave)
        val btnDelete: Button = inflation.findViewById(R.id.btnAddCancel)
        btnEdit.text = "Edit"
        btnDelete.text = "Delete"
        btnDelete.setBackgroundColor(Color.RED)

        return inflation
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnEdit: Button = requireView().findViewById(R.id.btnAddSave)
        val btnDelete: Button = requireView().findViewById(R.id.btnAddCancel)

        val name : EditText = requireView().findViewById(R.id.inputAddInvoiceName)
        name.isEnabled = false
        name.isClickable = false
        val spinner: Spinner = requireView().findViewById(R.id.inputSpinner)
        spinner.isEnabled = false
        spinner.isClickable = false
        val dateTime: RadioButton = requireView().findViewById(R.id.inputDateTime)
        dateTime.isEnabled = false
        dateTime.isClickable = false
        val usage: EditText = requireView().findViewById(R.id.inputAddInvoiceUsage)
        usage.isEnabled = false
        usage.isClickable = false
        val contributor: EditText = requireView().findViewById(R.id.inputAddContributor)
        contributor.isEnabled = false
        contributor.isClickable = false
        val storageLocation: EditText = requireView().findViewById(R.id.inputAddStorageLocation)
        storageLocation.isEnabled = false
        storageLocation.isClickable = false
        val optional: RadioButton = requireView().findViewById(R.id.inputAddOptional)
        optional.isEnabled = false
        optional.isClickable = false

        val args = arguments?.getLong("id")

        var data: InvoiceModel? = null
        GlobalScope.launch {
            if (arguments != null) {
                val dao = context?.let { it1 -> AppDatabase.getDatabase(it1).getInvoiceDao() }
                data = args?.let { dao?.getID(it) }
                Log.i("test", "GETTING DATA FROM $data")
            }

            name.setText(data?.invoiceName)
            // TODO: Spinner ausgeben, aber wie?
            dateTime.isChecked = true
            usage.setText(data?.invoiceUsage)
            contributor.setText(data?.invoiceContributor)
            storageLocation.setText(data?.invoiceStorageLocation)
            if (data?.invoiceForEnterprise == true) {
                optional.isChecked = true
            }
        }



        btnEdit.setOnClickListener {
            btnEdit.text = "Save"
            btnEdit.setBackgroundColor(Color.GREEN)

            name.isEnabled = true
            name.isClickable = true
            spinner.isEnabled = true
            spinner.isClickable = true
            dateTime.isEnabled = true
            dateTime.isClickable = true
            usage.isEnabled = true
            usage.isClickable = true
            contributor.isEnabled = true
            contributor.isClickable = true
            storageLocation.isEnabled = true
            storageLocation.isClickable = true
            optional.isEnabled = true
            optional.isClickable = true

            btnEdit.setOnClickListener {
                // Überarbeiten und Inserten
            }
        }

        btnDelete.setOnClickListener {

        }

    }

}