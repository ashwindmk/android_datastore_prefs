package com.ashwin.android.datastoreprefs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import java.util.*

private const val SUB_TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]

        mainViewModel.counter.observe(this, { count ->
            // This will be observed if any value in the data-store preference changes.
            // This newly observed value will be the same if some other key-value is updated.
            Log.d(Constant.APP_TAG, "$SUB_TAG: counter.onChange: $count")
        })

        mainViewModel.theme.observe(this, { theme ->
            // This will be observed if any value in the data-store preference changes.
            // This newly observed value will be the same if some other key-value is updated.
            Log.d(Constant.APP_TAG, "$SUB_TAG: theme.onChange: $theme")
        })

        val changeButton = findViewById<Button>(R.id.change_button)
        changeButton.setOnClickListener {
            val theme = Theme.values()[Random().nextInt(4)]
            mainViewModel.updateTheme(theme)  // This will invoke both counter.observe (with same old value) and theme.observe (with new value).

            mainViewModel.incrementCounter()  // This will invoke both theme.observe (with same old value) and counter.observe (with new value).
        }
    }
}
