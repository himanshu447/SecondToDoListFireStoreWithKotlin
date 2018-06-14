package com.example.knoxpo.secondtodolistfirestore.activty

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import com.example.knoxpo.secondtodolistfirestore.R

abstract class SingleFragmentActivity : AppCompatActivity() {

    abstract fun getFragment() : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_singlefragment)

        var fm: FragmentManager = supportFragmentManager

        var extFragment: Fragment? = fm.findFragmentById(R.id.frame_container)

        if(extFragment==null)
        {
            fm.beginTransaction()
                    .replace(R.id.frame_container,getFragment())
                    .commit()
        }
    }
}
