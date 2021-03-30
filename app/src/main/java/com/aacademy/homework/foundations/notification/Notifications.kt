package com.aacademy.homework.foundations.notification

import com.aacademy.homework.entities.MovieParcelable

interface Notifications {

    fun showNotification(movie: MovieParcelable)
    fun dismissNotification(movieId: Long)
}
