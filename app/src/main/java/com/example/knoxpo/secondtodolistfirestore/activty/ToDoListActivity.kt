package com.example.knoxpo.secondtodolistfirestore.activty

import android.support.v4.app.Fragment
import com.example.knoxpo.secondtodolistfirestore.fragment.ToDoListFragment


class ToDoListActivity : SingleFragmentActivity() {
    override fun getFragment(): Fragment {
        return ToDoListFragment()
    }
}