package com.aacademy.homework.ui.activities

import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.aacademy.homework.MyApp.Companion.THEME
import com.aacademy.homework.R
import com.aacademy.homework.R.anim
import com.aacademy.homework.R.id
import com.aacademy.homework.databinding.ActivityMainBinding
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails
import com.aacademy.homework.ui.movielist.FragmentMoviesList
import com.aacademy.homework.utils.viewBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }

    private val binding by viewBinding(ActivityMainBinding::inflate)

    private var detailsFragmentOpened = false

    override fun onCreate(savedInstanceState: Bundle?) {
        delegate.localNightMode = prefs.getInt(THEME, MODE_NIGHT_UNSPECIFIED)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        savedInstanceState ?: supportFragmentManager
            .beginTransaction()
            .add(id.flContainer, FragmentMoviesList.newInstance(), FRAGMENT_TAG)
            .commit()
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
            finishAfterTransition()
        } else {
            detailsFragmentOpened = false
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_list, menu)
        return true
    }

    private var lastTimeOptionsItemSelected: Long = 0
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            id.theme -> {
                if (SystemClock.elapsedRealtime() - lastTimeOptionsItemSelected > 1000) {
                    lastTimeOptionsItemSelected = SystemClock.elapsedRealtime()
                    changeTheme(findViewById(id.theme))
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        title = ""
    }

    private fun changeTheme(view: View) {
        if (detailsFragmentOpened) return
        lifecycleScope.launch(Dispatchers.IO) {
            val w = binding.flContainer.measuredWidth
            val h = binding.flContainer.measuredHeight
            val bitmap = Bitmap.createBitmap(w, h, ARGB_8888)
            val canvas = Canvas(bitmap)
            binding.flContainer.draw(canvas)

            val location = IntArray(2)
            view.getLocationOnScreen(location)

            delegate.localNightMode = when (delegate.localNightMode) {
                MODE_NIGHT_YES -> MODE_NIGHT_NO
                else -> MODE_NIGHT_YES
            }

            withContext(Dispatchers.Main) {
                binding.screenshot.apply {
                    setOnClickListener { }
                    scaleType = ImageView.ScaleType.MATRIX
                    setImageBitmap(bitmap)
                }
            }

            supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)?.let { fr ->
                fr.arguments = FragmentMoviesList.createBundle(
                    location[0] + view.measuredWidth / 2,
                    location[1] + view.measuredHeight / 2,
                    sqrt((w * w + h * h).toDouble()).toFloat()
                )
                supportFragmentManager
                    .beginTransaction()
                    .detach(fr)
                    .attach(fr)
                    .commit()
            }

            prefs.edit().putInt(THEME, delegate.localNightMode).apply()
        }
    }

    fun openMovieDetail(movieId: Long) {
        if (!detailsFragmentOpened) {
            detailsFragmentOpened = true
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
    }

    companion object {

        private const val FRAGMENT_TAG = "FragmentTag"
    }
}
