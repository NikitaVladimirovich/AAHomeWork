package com.aacademy.homework.ui.activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.appcompat.widget.Toolbar
import androidx.preference.PreferenceManager
import com.aacademy.homework.MyApp.Companion.THEME
import com.aacademy.homework.R
import com.aacademy.homework.R.anim
import com.aacademy.homework.R.id
import com.aacademy.homework.databinding.ActivityMainBinding
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails
import com.aacademy.homework.ui.movielist.FragmentMoviesList
import com.aacademy.homework.utils.viewBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    private val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(applicationContext) }

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        if (bitmap == null) delegate.localNightMode = prefs.getInt(THEME, MODE_NIGHT_UNSPECIFIED)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        savedInstanceState ?: supportFragmentManager
            .beginTransaction()
            .add(id.flContainer, FragmentMoviesList.newInstance(), FRAGMENT_TAG)
            .commit()

        animateThemeChangeIfNeeded()
    }

    override fun onBackPressed() {
        val backStackEntryCount = supportFragmentManager.backStackEntryCount
        if (backStackEntryCount == 0) {
            finishAfterTransition()
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
            id.theme -> changeTheme(findViewById(id.theme))
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        title = ""
    }

    private fun animateThemeChangeIfNeeded() {
        if (bitmap != null) {
            val screenshot = findViewById<ImageView>(R.id.screenshot)
            screenshot.setOnClickListener { }
            screenshot.setImageBitmap(bitmap)
            screenshot.scaleType = ImageView.ScaleType.MATRIX
            screenshot.visibility = View.VISIBLE

            val listener = object : View.OnAttachStateChangeListener {
                override fun onViewAttachedToWindow(v: View?) {
                    val anim = ViewAnimationUtils.createCircularReveal(
                        screenshot,
                        intent.getIntExtra(POSITION_X, 0),
                        intent.getIntExtra(POSITION_Y, 0),
                        intent.getFloatExtra(RADIUS, 0f),
                        0f
                    )
                    anim.duration = 1000
                    anim.interpolator = AccelerateDecelerateInterpolator()
                    anim.addListener(
                        object : AnimatorListenerAdapter() {
                            override fun onAnimationEnd(animation: Animator?) {
                                super.onAnimationEnd(animation)
                                screenshot.setImageDrawable(null)
                                screenshot.visibility = View.GONE
                                bitmap?.recycle()
                                bitmap = null
                            }
                        }
                    )
                    anim.start()
                }

                override fun onViewDetachedFromWindow(v: View?) {}
            }
            screenshot.addOnAttachStateChangeListener(listener)
        }
    }

    private fun changeTheme(view: View) {
        val w = binding.flContainer.measuredWidth
        val h = binding.flContainer.measuredHeight
        bitmap = Bitmap.createBitmap(w, h, ARGB_8888)
        val canvas = Canvas(bitmap!!)
        binding.flContainer.draw(canvas)

        val location = IntArray(2)
        view.getLocationOnScreen(location)
        intent.apply {
            putExtra(POSITION_X, (location[0] + view.measuredWidth / 2))
            putExtra(POSITION_Y, (location[1] + view.measuredHeight / 2))
            putExtra(RADIUS, sqrt((w * w + h * h).toDouble()).toFloat())
            addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        }

        delegate.localNightMode = when (delegate.localNightMode) {
            MODE_NIGHT_YES -> MODE_NIGHT_NO
            else -> MODE_NIGHT_YES
        }
        prefs.edit().putInt(THEME, delegate.localNightMode).apply()
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
        private const val POSITION_X = "x"
        private const val POSITION_Y = "y"
        private const val RADIUS = "radius"
        var bitmap: Bitmap? = null
    }
}
