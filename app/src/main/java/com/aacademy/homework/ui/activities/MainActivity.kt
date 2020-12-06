package com.aacademy.homework.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.WindowCompat
import com.aacademy.homework.R.anim
import com.aacademy.homework.R.id
import com.aacademy.homework.data.model.MoviePreviewWithTags
import com.aacademy.homework.databinding.ActivityMainBinding
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails
import com.aacademy.homework.ui.movielist.FragmentMoviesList

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        if (savedInstanceState == null)
            supportFragmentManager
                .beginTransaction()
                .add(id.flContainer, FragmentMoviesList.newInstance(), FRAGMENT_TAG)
                .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        title = ""
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    fun openMovieDetail(moviePreview: MoviePreviewWithTags) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                anim.fade_in,
                anim.fade_out,
                anim.fade_in,
                anim.fade_out
            )
            .add(id.flContainer, FragmentMoviesDetails.newInstance(moviePreview), FRAGMENT_TAG)
            .addToBackStack(null)
            .commit()
    }

    companion object {

        private const val FRAGMENT_TAG = "FragmentTag"
    }
}