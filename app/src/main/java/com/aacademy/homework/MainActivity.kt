package com.aacademy.homework

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.aacademy.homework.data.local.MockRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(R.id.flContainer, FragmentMoviesDetails.newInstance(MockRepository.getMovie()), "Tag")
                .addToBackStack(null)
                .commit()
    }
}