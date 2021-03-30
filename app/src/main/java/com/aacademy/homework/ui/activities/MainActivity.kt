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
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.aacademy.data.preferences.MyPreference
import com.aacademy.homework.R
import com.aacademy.homework.R.id
import com.aacademy.homework.databinding.ActivityMainBinding
import com.aacademy.homework.entities.MovieParcelable
import com.aacademy.homework.extensions.viewBinding
import com.aacademy.homework.ui.movielist.FragmentMoviesList
import com.aacademy.homework.ui.movielist.FragmentMoviesListDirections
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
        if (supportFragmentManager.findFragmentById(id.nav_host_fragment)?.childFragmentManager?.backStackEntryCount == 0) {
            finishAfterTransition()
        } else {
            findNavController(
                this@MainActivity,
                id.nav_host_fragment
            ).navigateUp()
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
            val w = binding.navHostFragment.measuredWidth
            val h = binding.navHostFragment.measuredHeight
            val bitmap = Bitmap.createBitmap(w, h, ARGB_8888)
            val canvas = Canvas(bitmap)
            binding.navHostFragment.draw(canvas)

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

            supportFragmentManager.findFragmentById(id.nav_host_fragment)?.childFragmentManager?.let { fragmentManager ->
                fragmentManager.fragments.firstOrNull()
                    ?.let { fr ->
                        fr.arguments = FragmentMoviesList.createBundle(
                            location[0] + view.measuredWidth / 2,
                            location[1] + view.measuredHeight / 2,
                            sqrt((w * w + h * h).toDouble()).toFloat()
                        )
                        fragmentManager
                            .beginTransaction()
                            .detach(fr)
                            .attach(fr)
                            .commit()
                    }
            }

            prefs.appTheme = delegate.localNightMode
        }
    }

    fun openMovieDetail(sharedView: View, movie: MovieParcelable) {
        val action = FragmentMoviesListDirections.actionFragmentMoviesListToFragmentMoviesDetails(movie)
        val extras = FragmentNavigatorExtras(sharedView to getString(R.string.movie_card_detail_transition_name))
        findNavController(
            this@MainActivity,
            id.nav_host_fragment
        ).navigate(
            action,
            extras
        )
    }

    private fun openMovieDetail(arguments: Bundle) {
        findNavController(
            this@MainActivity,
            id.nav_host_fragment
        ).navigate(
            id.action_fragmentMoviesList_to_fragmentMoviesDetails,
            arguments
        )
    }

    fun openMoviesList() {
        findNavController(
            this@MainActivity,
            id.nav_host_fragment
        ).navigate(
            id.action_fragmentSplash_to_fragmentMoviesList
        )
    }
}
