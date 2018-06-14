package com.example.knoxpo.secondtodolistfirestore.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.knoxpo.secondtodolistfirestore.R
import kotlinx.android.synthetic.main.add_new_item.*
import kotlinx.android.synthetic.main.add_new_item.view.*
import kotlinx.android.synthetic.main.todo_list_row.*
import kotlin.math.ln

class AddNewFragment : Fragment() {

    companion object {

        fun newInstance(refID: String?, fname: String, lname: String, check: Boolean): Fragment {

            val b =Bundle()
            b.putString("refid",refID)
            b.putString("fname",fname)
            b.putString("lname",lname)
            b.putBoolean("check",check)

            val addNewFragment  = AddNewFragment()
            addNewFragment.arguments = b

            return addNewFragment
        }

    }

    var mCallBack : CallBack? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        mCallBack = activity as CallBack
    }

    override fun onDetach() {
        super.onDetach()

        mCallBack = null
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val refID = arguments?.getString("refid")
        val fname=arguments?.getString("fname")
        val lname=arguments?.getString("lname")
        val check=arguments?.getBoolean("check")


        val view : View = inflater.inflate(R.layout.add_new_item,container,false)

        val textViewNewITemFname: EditText =view.textViewNewITemFname
        val textViewNewITemLname: EditText =view.textViewNewITemLname
        val checkboxnewItem: CheckBox= view.checkboxnewItem
        val buttonSave:Button=view.buttonSave
        val buttonclear:Button=view.buttonclear

        //Toast.makeText(activity,"in fragment ref id" + refID,Toast.LENGTH_SHORT).show()

        if(refID==null)
        {

        }
        else
        {
            textViewNewITemFname.setText(fname)
            textViewNewITemLname.setText(lname)
            checkboxnewItem.isChecked = check!!

        }

        buttonSave.setOnClickListener {

            val mFname = textViewNewITemFname.text.toString()
            val mLname = textViewNewITemLname.text.toString()
            val check = checkboxnewItem.isChecked

            if(refID!= null)
            {

                mCallBack?.onUpdate(refID,mFname,mLname,check)

            }

            mCallBack?.onSave(mFname,mLname,check)

        }

        buttonclear.setOnClickListener {


        }
        return view
    }


    interface CallBack
    {
        fun onSave(fname : String,lname : String , check : Boolean)

        fun onUpdate(refID: String, mFname: String, mLname: String, check: Boolean)
    }

}