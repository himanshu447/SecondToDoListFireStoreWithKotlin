package com.example.knoxpo.secondtodolistfirestore.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.knoxpo.secondtodolistfirestore.R
import com.example.knoxpo.secondtodolistfirestore.adapater.ToDoListAdapater
import com.example.knoxpo.secondtodolistfirestore.model.ToDoCollections
import com.example.knoxpo.secondtodolistfirestore.model.ToDoModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.todo_list.view.*
import android.support.v7.widget.DividerItemDecoration
import android.widget.Toast
import com.example.knoxpo.secondtodolistfirestore.activty.AddNewItemActivity
import com.google.firebase.firestore.DocumentReference


class ToDoListFragment : Fragment(), ToDoListAdapater.ShowDialg {


    val REQUEST_CODE_NEW_ITEM_ACTIVTY: Int = 123

    val REQUEST_CODE_UPDATE = 456

    val REQUEST_CODE_FOR_TARGET: Int = 321

    var db: FirebaseFirestore = FirebaseFirestore.getInstance()

    var mToDoAdapater: ToDoListAdapater? = null

    var mList: ArrayList<ToDoModel>? = null

    var recycler: RecyclerView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.todo_list, container, false)

        recycler = view.recycler_view

        intilation()

        fetchData()

        view.fab.setOnClickListener {

            val intent = Intent(activity, AddNewItemActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_NEW_ITEM_ACTIVTY)
        }


        return view
    }

    private fun intilation() {

        mList = ArrayList()

        mToDoAdapater = ToDoListAdapater(mList!!)

        val layout: RecyclerView.LayoutManager = LinearLayoutManager(activity)

        recycler?.layoutManager = layout

        recycler?.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))

        recycler?.adapter = mToDoAdapater

        mToDoAdapater?.mShowDialg = this


    }

    private fun addData(db: FirebaseFirestore, mToDoAdapater: ToDoListAdapater?, fname: String, lname: String, check: Boolean) {

        val map: MutableMap<String, Any> = mutableMapOf(
                ToDoCollections.Fields.FNAME to fname,
                ToDoCollections.Fields.LNAME to lname,
                ToDoCollections.Fields.CHECK to check
        )
        db.collection(ToDoCollections.NAME).add(map as Map<String, Any>)
        mToDoAdapater?.notifyDataSetChanged()
    }

    private fun fetchData() {

        db.collection(ToDoCollections.NAME)
                .orderBy(ToDoCollections.Fields.FNAME)
                .addSnapshotListener { querySnapshot, _ ->
                    mList?.clear()
                    if (querySnapshot != null) {
                        for (document in querySnapshot.documents) {
                            val mTodoModel = ToDoModel()
                            mTodoModel.id = document.id
                            mTodoModel.fName = document.get(ToDoCollections.Fields.FNAME).toString()
                            mTodoModel.lName = document.get(ToDoCollections.Fields.LNAME).toString()
                            mTodoModel.check = (document.get(ToDoCollections.Fields.CHECK) as Boolean)
                            mList?.add(mTodoModel)
                        }
                    }
                    mToDoAdapater?.notifyDataSetChanged()

                }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == REQUEST_CODE_NEW_ITEM_ACTIVTY && resultCode == Activity.RESULT_OK && data != null) {

            val fname = data.getStringExtra("fname")
            val lname = data.getStringExtra("lname")
            val check = data.getBooleanExtra("check", false)

            addData(db, mToDoAdapater, fname, lname, check)

        } else if (requestCode == REQUEST_CODE_FOR_TARGET && resultCode == Activity.RESULT_OK && data != null) {

            val model = data.getStringExtra("model")

            db.collection(ToDoCollections.NAME).document(model).delete()

        } else if (requestCode == REQUEST_CODE_UPDATE && resultCode == Activity.RESULT_OK && data != null) {

            val id = data.getStringExtra("refid")
            val fname = data.getStringExtra("fname")
            val lname = data.getStringExtra("lname")
            val check = data.getBooleanExtra("check", false)

            val ref: DocumentReference = db.collection(ToDoCollections.NAME).document(id)
            val updateMap: MutableMap<String, Any> = mutableMapOf(
                    ToDoCollections.Fields.LNAME to lname,
                    ToDoCollections.Fields.FNAME to fname,
                    ToDoCollections.Fields.CHECK to check
            )
            ref.update(updateMap).addOnCompleteListener {

                Toast.makeText(activity, "Succefully Update", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun showDialgForDelete(mToDoModel: String?) {

        val dialog: DialogFragment = AleartDialogFragment.newINstance(mToDoModel)

        val fm = this.activity?.supportFragmentManager

        dialog.show(fm, "dialog")

        dialog.setTargetFragment(this, REQUEST_CODE_FOR_TARGET)

    }

    override fun updateItem(mToDoModel: String?, fName: String, lName: String, check: Boolean) {

        val intent = Intent(activity, AddNewItemActivity::class.java)
        intent.putExtra("refid", mToDoModel)
        intent.putExtra("fname", fName)
        intent.putExtra("lname", lName)
        intent.putExtra("check", check)
        startActivityForResult(intent, REQUEST_CODE_UPDATE)
    }


}