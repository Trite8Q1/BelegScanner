package de.emgress.belegscanner

import android.os.Bundle
import androidx.fragment.app.Fragment
import kotlinx.coroutines.Job

abstract class BaseFragment : Fragment() {
    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}