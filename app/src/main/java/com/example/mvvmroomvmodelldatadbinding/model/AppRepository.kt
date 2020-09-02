package com.example.mvvmroomvmodelldatadbinding.model

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppRepository(application: Application) {

    private lateinit var genreDao: GenreDao
    private lateinit var movieDao: MovieDao

    private lateinit var genres: LiveData<List<Genre>>
    private lateinit var movies: LiveData<List<Movie>>

    init {
        val database = MoviesDatabase.InstanceDatabase.getInstance(application)
        genreDao = database.getGenreDao()
        movieDao = database.getMovieDao()
    }

    fun getGenres(): LiveData<List<Genre>> {
        return genreDao.getAllGenres()
    }
    fun insertGenre(genre: Genre) {
        GlobalScope.launch(Dispatchers.IO) {
            genreDao.insert(genre)
        }
    }
    fun updateGenre(genre: Genre){
        GlobalScope.launch(Dispatchers.IO) {
            genreDao.update(genre)
        }
    }
    fun deleteGenre(genre: Genre){
        GlobalScope.launch(Dispatchers.IO) {
            genreDao.delete(genre)
        }
    }

    fun getGenreMovies(genreId: Int): LiveData<List<Movie>> {
        return movieDao.getGenreMovies(genreId)
    }

    fun insertMovie(movie: Movie){
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.insert(movie)
        }
    }

    fun updateMovie(movie: Movie){
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.update(movie)
        }
    }

    fun deleteMovie(movie: Movie){
        GlobalScope.launch(Dispatchers.IO) {
            movieDao.delete(movie)
        }
    }
}



