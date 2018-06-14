package com.example.knoxpo.secondtodolistfirestore.adapater

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import com.example.knoxpo.secondtodolistfirestore.R
import com.example.knoxpo.secondtodolistfirestore.model.ToDoModel
import kotlinx.android.synthetic.main.todo_list_row.view.*

class ToDoListAdapater(var mToDoList: List<ToDoModel>) : RecyclerView.Adapter<ToDoListAdapater.MyViewHolder>() {

    var mShowDialg: ShowDialg? = null

    override fun getItemCount() = mToDoList.size

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val mToDoModel: ToDoModel = mToDoList[p1]
        p0.bindData(mToDoModel)
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {

        val view: View = LayoutInflater.from(p0.context).inflate(R.layout.todo_list_row, p0, false)

        return MyViewHolder(view)
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var textViewFName: TextView = itemView.textViewFName
        var textViewLName: TextView = itemView.textViewLName
        var checkBox: CheckBox = itemView.checkBox
        var imageButtonDelete: ImageButton = itemView.imageButtonDelete
        var imageButtonEdit: ImageButton = itemView.imageButtonEdit

        fun bindData(mToDoModel: ToDoModel) {

            textViewFName.text = mToDoModel.fName
            textViewLName.text = mToDoModel.lName
            checkBox.isChecked = mToDoModel.check

            imageButtonEdit.setOnClickListener {

                mShowDialg?.updateItem(mToDoModel.id,mToDoModel.fName,mToDoModel.lName,mToDoModel.check)
            }

            imageButtonDelete.setOnClickListener {

                mShowDialg?.showDialgForDelete(mToDoModel.id)

            }
        }

    }

    interface ShowDialg {
        fun showDialgForDelete(mToDoModel: String?)

        fun updateItem(mToDoModel: String?, fName: String, lName: String, check: Boolean)
    }
}