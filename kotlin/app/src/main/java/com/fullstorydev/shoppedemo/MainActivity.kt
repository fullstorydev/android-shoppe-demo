package com.fullstorydev.shoppedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.fullstory.FS
import com.fullstory.FSOnReadyListener
import com.fullstory.FSSessionData
import com.fullstorydev.shoppedemo.ui.main.MainFragment

class MainActivity : AppCompatActivity(), FSOnReadyListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
        }

        FS.setReadyListener(this)
    }

    override fun onReady(sessionData: FSSessionData?) {
        Log.d("MainActivity", "SessionURL is: " + FS.getCurrentSessionURL())
    }
}
