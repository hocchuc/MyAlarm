package com.hocchuc.myalarm

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.hocchuc.myalarm.R

class MainActivity : AppCompatActivity() {
    companion object {
        const val ALARM_REQUEST_CODE = 99;
        const val ALARM_MODE = "IS_ALARMING";
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        if(intent.getBooleanExtra(ALARM_MODE, false)){
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}