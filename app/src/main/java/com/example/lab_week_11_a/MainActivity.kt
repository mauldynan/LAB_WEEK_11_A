package com.example.lab_week_11_a

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Ganti pengambilan wrapper data dari PreferenceApplication ke SettingsApplication
        // 2. Ganti 'preferenceWrapper' menjadi 'settingsStore'
        val settingsStore = (application as SettingsApplication).settingsStore

        // Create the view model instance with the settings store as the
        // constructor parameter
        // To pass the settings store to the view model, we need to use
        // a view model factory
        val preferenceViewModel = ViewModelProvider(this, object :
            ViewModelProvider.Factory {
            // Ganti PreferenceViewModel dengan SettingsViewModel
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SettingsViewModel(settingsStore) as T
            }
            // Ganti PreferenceViewModel::class.java dengan SettingsViewModel::class.java
        })[SettingsViewModel::class.java]

        // Observe the text live data
        // Ganti preferenceViewModel.getText().observe(this menjadi preferenceViewModel.textLiveData.observe(this
        preferenceViewModel.textLiveData.observe(this
        ) {
            // Update the text view when the text live data changes
            findViewById<TextView>(
                R.id.activity_main_text_view
            ).text = it
        }

        findViewById<Button>(R.id.activity_main_button).setOnClickListener {
            // Save the text when the button is clicked
            preferenceViewModel.saveText(
                findViewById<EditText>(R.id.activity_main_edit_text)
                    .text.toString()
            )
        }
    }
}