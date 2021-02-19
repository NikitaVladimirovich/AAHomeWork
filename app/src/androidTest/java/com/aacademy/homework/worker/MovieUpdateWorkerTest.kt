package com.aacademy.homework.worker

import android.content.Context
import androidx.hilt.work.HiltWorkerFactory
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker.Result
import androidx.work.testing.TestListenableWorkerBuilder
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MovieUpdateWorkerTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    private lateinit var context: Context

    private lateinit var movieUpdateWorker: MovieUpdateWorker

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        hiltRule.inject()
        movieUpdateWorker =
            TestListenableWorkerBuilder<MovieUpdateWorker>(context).setWorkerFactory(hiltWorkerFactory).build()
    }

    @Test
    fun testMovieUpdateWorkerRunning() {
        runBlocking {
            val result = movieUpdateWorker.doWork()
            assertThat(result, `is`(Result.success()))
        }
    }
}
