package com.example.knoxpo.secondtodolistfirestore.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.Toast

class AleartDialogFragment : DialogFragment() {

    companion object {

        fun newINstance(mToDoModel: String?): DialogFragment {

            var bundle = Bundle()
            bundle.putString("id", mToDoModel)

            var dialog = AleartDialogFragment()
            dialog.arguments = bundle
            return dialog
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {


        val model = arguments?.getString("id")

        return AlertDialog.Builder(activity!!)
                .setTitle("Delete Item")
                .setMessage("Are you sure you want to delete this item ? ")
                .setNegativeButton("no", DialogInterface.OnClickListener { dialogInterface, i -> dismiss() })
                .setPositiveButton("yes", DialogInterface.OnClickListener { dialogInterface, i ->

                    val intent = Intent()
                    intent.putExtra("model", model)
                    targetFragment!!.onActivityResult(targetRequestCode, Activity.RESULT_OK, intent)
                    dismiss()

                })
                .create()
    }


}