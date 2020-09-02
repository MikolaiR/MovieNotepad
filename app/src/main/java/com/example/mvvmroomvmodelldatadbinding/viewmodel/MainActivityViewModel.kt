package com.example.mvvmroomvmodelldatadbinding.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.mvvmroomvmodelldatadbinding.model.AppRepository
import com.example.mvvmroomvmodelldatadbinding.model.Genre
import com.example.mvvmroomvmodelldatadbinding.model.Movie

class MainActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val appRepository: AppRepository = AppRepository(application)

    fun getGenres(): LiveData<List<Genre>> {
        return appRepository.getGenres()
    }

    fun getGenreMovies(genreId: Int): LiveData<List<Movie>> {
        return appRepository.getGenreMovies(genreId)
    }

    fun addNewMovie(movie: Movie){
        appRepository.insertMovie(movie)
    }
    fun deleteMovie(movie: Movie){
        appRepository.deleteMovie(movie)
    }
    fun updateMovie(movie: Movie){
        appRepository.updateMovie(movie)
    }

}