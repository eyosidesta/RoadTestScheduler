package com.example.roadtestscheduler

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SampleDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create an image URL for demonstration purposes
        val imageUrl = "https://example.com/sample_certificate.jpg"

        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Your License Certificate")
            .setMessage("Here is your driver license certificate.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setView(R.layout.dialog_fragment_layout) // Set the layout for your dialog
            .create()
    }
}

