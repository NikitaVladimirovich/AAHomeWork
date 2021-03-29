package com.aacademy.homework.foundations.notification

import com.aacademy.homework.data.model.Movie

interface Notifications {

    fun showNotification(movie: Movie)
    fun dismissNotification(movieId: Long)
}
