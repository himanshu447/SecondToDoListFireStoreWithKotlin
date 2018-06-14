package com.example.knoxpo.secondtodolistfirestore.activty

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.example.knoxpo.secondtodolistfirestore.fragment.AddNewFragment

class AddNewItemActivity : SingleFragmentActivity(), AddNewFragment.CallBack {


    override fun getFragment(): Fragment {

        val refID = intent.getStringExtra("refid")
        val fname = intent.getStringExtra("fname")
        val lname = intent.getStringExtra("lname")
        val check = intent.getBooleanExtra("check", false)

        if (refID != null)
            return AddNewFragment.newInstance(refID, fname, lname, check)
        else
            return AddNewFragment()
    }

    override fun onSave(fname: String, lname: String, check: Boolean) {
        val intent = Intent()
        intent.putExtra("fname", fname)
        intent.putExtra("lname", lname)
        intent.putExtra("check", check)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onUpdate(refID: String, mFname: String, mLname: String, check: Boolean) {

        val intent = Intent()
        intent.putExtra("refid", refID)
        intent.putExtra("fname", mFname)
        intent.putExtra("lname", mLname)
        intent.putExtra("check", check)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

}