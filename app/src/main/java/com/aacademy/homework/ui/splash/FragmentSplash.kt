package com.aacademy.homework.ui.splash

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.aacademy.homework.R
import com.aacademy.homework.databinding.FragmentSplashBinding
import com.aacademy.homework.extensions.viewBinding
import com.aacademy.homework.ui.activities.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@ExperimentalCoroutinesApi
@FlowPreview
class FragmentSplash : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val drawable = binding.image.drawable
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
        lifecycleScope.launch {
            delay(3000)
            withContext(Dispatchers.Main) {
                (activity as MainActivity).openMoviesList()
            }
        }
    }
}
