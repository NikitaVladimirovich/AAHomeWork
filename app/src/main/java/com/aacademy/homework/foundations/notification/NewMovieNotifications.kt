package com.aacademy.homework.foundations.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import androidx.annotation.WorkerThread
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_HIGH
import androidx.core.net.toUri
import com.aacademy.homework.R
import com.aacademy.homework.data.model.Movie
import com.aacademy.homework.ui.activities.MainActivity
import com.aacademy.homework.ui.moviedetail.FragmentMoviesDetails
import com.bumptech.glide.Glide
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber

@ExperimentalCoroutinesApi
@FlowPreview
class NewMovieNotifications(private val context: Context) : Notifications {

    private val notificationManagerCompat: NotificationManagerCompat =
        NotificationManagerCompat.from(context)

    init {
        if (notificationManagerCompat.getNotificationChannel(CHANNEL_NEW_MOVIES) == null) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannelCompat.Builder(CHANNEL_NEW_MOVIES, IMPORTANCE_HIGH)
                    .setName(context.getString(R.string.channel_new_movie))
                    .setDescription(context.getString(R.string.channel_new_movie_description))
                    .build()
            )
        }
    }

    @WorkerThread
    override fun showNotification(movie: Movie) {
        val contentUri = "app://aacademy.com/movies/${movie.id}".toUri()

        val futureTarget = Glide.with(context)
            .asBitmap()
            .load(movie.poster)
            .submit()

        val bitmap = try {
            futureTarget.get()
        } catch (e: Exception) {
            Timber.e(e)
        }

        Glide.with(context).clear(futureTarget)

        val builder = NotificationCompat.Builder(context, CHANNEL_NEW_MOVIES)
            .setContentTitle(movie.title)
            .setContentText(context.getString(R.string.new_movie_content_text))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(bitmap as Bitmap?)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setOnlyAlertOnce(true)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    context,
                    REQUEST_CONTENT,
                    Intent(context, MainActivity::class.java)
                        .setAction(Intent.ACTION_VIEW)
                        .setData(contentUri)
                        .putExtra(FragmentMoviesDetails.MOVIE_ARGUMENT, movie),
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            )

        notificationManagerCompat.notify(MOVIE_TAG, movie.id.toInt(), builder.build())
    }

    override fun dismissNotification(movieId: Long) {
        notificationManagerCompat.cancel(MOVIE_TAG, movieId.toInt())
    }

    companion object {

        private const val CHANNEL_NEW_MOVIES = "new_movies"

        private const val REQUEST_CONTENT = 1

        private const val MOVIE_TAG = "movie"
    }
}
