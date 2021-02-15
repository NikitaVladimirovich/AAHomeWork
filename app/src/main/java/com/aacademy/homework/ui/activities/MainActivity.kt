package com.aacademy.homework.ui.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Bitmap.Config.ARGB_8888
import android.graphics.Canvas
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.lifecycle.lifecycleScope
import com.aacademy.homework.R
import com.aacademy.homework.R.anim
import com.aacademy.homework.R.id
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.data.preferences.MyPreference
import com.aacademy.homework.databinding.ActivityMainBinding
import com.aacademy.homework.extensions.open
import com.aacademy.homework.extensions.viewBinding
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails
import com.aacademy.homework.ui.movielist.FragmentMoviesList
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.math.sqrt

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var prefs: MyPreference

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AAHomeWork)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        savedInstanceState ?: supportFragmentManager.open {
            add(id.container, FragmentMoviesList.newInstance(), FRAGMENT_TAG)
            intent?.let(::handleIntent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.let(::handleIntent)
    }

    private fun handleIntent(intent: Intent) {
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                intent.data?.lastPathSegment?.toLongOrNull()?.let {
                    intent.extras?.let(::openMovieDetail)
                }
            }
        }
    }

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 0) {
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
        }
        return super.onOptionsItemSelected(item)
    }

    fun showLoading() {
        binding.progressView.visibility = VISIBLE
    }

    fun hideLoading() {
        binding.progressView.visibility = GONE
    }

    internal fun changeTheme(view: View) {
        lifecycleScope.launch(Dispatchers.IO) {
            val w = binding.container.measuredWidth
            val h = binding.container.measuredHeight
            val bitmap = Bitmap.createBitmap(w, h, ARGB_8888)
            val canvas = Canvas(bitmap)
            binding.container.draw(canvas)

            val location = IntArray(2)
            view.getLocationOnScreen(location)

            delegate.localNightMode = when (prefs.appTheme) {
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

            prefs.appTheme = delegate.localNightMode
        }
    }

    fun openMovieDetail(movie: Movie) {
        supportFragmentManager.open {
            setCustomAnimations(
                anim.fade_in,
                anim.fade_out,
                anim.fade_in,
                anim.fade_out
            )
            add(id.container, FragmentMoviesDetails.newInstance(movie), FRAGMENT_TAG)
            addToBackStack(null)
        }
    }

    fun openMovieDetail(arguments: Bundle) {
        supportFragmentManager.open {
            setCustomAnimations(
                anim.fade_in,
                anim.fade_out,
                anim.fade_in,
                anim.fade_out
            )
            add(id.container, FragmentMoviesDetails.newInstance(arguments), FRAGMENT_TAG)
            addToBackStack(null)
        }
    }

    companion object {

        private const val FRAGMENT_TAG = "FragmentTag"
    }
}
