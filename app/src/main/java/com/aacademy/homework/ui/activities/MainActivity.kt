package com.aacademy.homework.ui.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.aacademy.homework.R.anim
import com.aacademy.homework.R.id
import com.aacademy.homework.databinding.ActivityMainBinding
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails
import com.aacademy.homework.ui.movielist.FragmentMoviesList
import com.aacademy.homework.utils.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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

    fun openMovieDetail(movieId: Long) {
        supportFragmentManager
            .beginTransaction()
            .setCustomAnimations(
                anim.fade_in,
                anim.fade_out,
                anim.fade_in,
                anim.fade_out
            )
            .add(id.flContainer, FragmentMoviesDetails.newInstance(movieId), FRAGMENT_TAG)
            .addToBackStack(null)
            .commit()
    }

    companion object {

        private const val FRAGMENT_TAG = "FragmentTag"
    }
}
