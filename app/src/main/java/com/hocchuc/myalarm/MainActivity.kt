package com.hocchuc.myalarm

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity() {

    companion object {
        const val ALARM_REQUEST_CODE = 99;
        const val ALARM_MODE = "IS_ALARMING";
    }

    var isAlarmFired = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        handleIntent()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        handleIntent()
    }

    private fun handleIntent() {
        if (!isAlarmFired &&
            intent?.getBooleanExtra(ALARM_MODE, false) == true
        ) {
            isAlarmFired = true
            Log.d("MyAlarm", "Alarm Screen Running")
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }
}