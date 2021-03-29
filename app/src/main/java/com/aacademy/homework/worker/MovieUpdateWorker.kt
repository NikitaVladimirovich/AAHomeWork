package com.aacademy.homework.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.aacademy.homework.data.DataRepository
import com.aacademy.homework.foundations.notification.Notifications
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import timber.log.Timber

@HiltWorker
class MovieUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val dataRepository: DataRepository,
    private val dispatcher: CoroutineDispatcher,
    private val notifications: Notifications
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result = withContext(dispatcher) {
        try {
            Timber.d("Run update movies")
            val localMovies = dataRepository.getMoviesFromDB()
            val newMovies = dataRepository.getMoviesFromAPI(page = 1).second
            val newMovie = newMovies.minus(localMovies).firstOrNull()
            if (newMovie != null) {
                notifications.showNotification(newMovie)
            } else {
                notifications.showNotification(newMovies.first())
            }

            Result.success()
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure()
        }
    }
}
