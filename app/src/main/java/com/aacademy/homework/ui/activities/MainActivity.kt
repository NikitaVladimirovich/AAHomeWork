package com.aacademy.homework.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.aacademy.homework.R.anim
import com.aacademy.homework.R.id
import com.aacademy.homework.data.model.MoviePreviewWithGenres
import com.aacademy.homework.databinding.ActivityMainBinding
import com.aacademy.homework.extensions.open
import com.aacademy.homework.extensions.viewBinding
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails
import com.aacademy.homework.ui.movielist.FragmentMoviesList
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        savedInstanceState ?: supportFragmentManager.open {
            add(id.flContainer, FragmentMoviesList.newInstance(), FRAGMENT_TAG)
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            finishAfterTransition()
        } else {
            hideLoading()
            super.onBackPressed()
        }
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

    fun showLoading() {
        binding.progressView.visibility = VISIBLE
    }

    fun hideLoading() {
        binding.progressView.visibility = GONE
    }

    fun openMovieDetail(moviePreview: MoviePreviewWithGenres) {
        supportFragmentManager.open {
            setCustomAnimations(
                anim.fade_in,
                anim.fade_out,
                anim.fade_in,
                anim.fade_out
            )
            add(id.flContainer, FragmentMoviesDetails.newInstance(moviePreview), FRAGMENT_TAG)
            addToBackStack(null)
        }
    }

    companion object {

        private const val FRAGMENT_TAG = "FragmentTag"
    }
}
