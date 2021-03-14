package com.hocchuc.myalarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.hocchuc.myalarm.MainActivity.Companion.ALARM_REQUEST_CODE
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), TimePickerDialog.OnTimeSetListener {

    private lateinit var alarmIntent: PendingIntent
    var isRepeat = false;
    var tvChosenTime: TextView? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_first).setOnClickListener {
            val calendar: Calendar = Calendar.getInstance().apply {
                TimePickerDialog(context, this@FirstFragment,
                    this.get(Calendar.HOUR_OF_DAY),
                    this.get(Calendar.MINUTE)+1,
                    true).show()
            }
        }
        view.findViewById<CheckBox>(R.id.cbToggleTime).setOnCheckedChangeListener{ _,checked ->
            isRepeat = checked
        }
        tvChosenTime = view.findViewById(R.id.textview_first);
        tvChosenTime?.text = getString(R.string.chosen_time, "_","_")

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val alarmManager =
            context?.getSystemService(Context.ALARM_SERVICE) as? AlarmManager
        alarmIntent = Intent(context, AlarmReceiver::class.java).let { intent ->
            PendingIntent.getBroadcast(context, ALARM_REQUEST_CODE, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        }
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, hourOfDay)
            set(Calendar.MINUTE, minute)
        }
        tvChosenTime?.text = getString(R.string.chosen_time, "$hourOfDay : $minute",
        if(isRepeat) getString(R.string.mode_on) else getString(R.string.mode_off)
        )
        Log.d("Set Alarm", tvChosenTime?.text?.toString() ?: "");
        if(isRepeat){
            alarmManager?.setExact(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                alarmIntent)
        } else {
            alarmManager?.setRepeating(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                        alarmIntent
            )
        }
    }
}