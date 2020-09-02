package com.example.mvvmroomvmodelldatadbinding.model

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface MovieDao {

    @Insert
    fun insert(movie: Movie)

    @Update
    fun update(movie: Movie)

    @Delete
    fun delete(movie: Movie)

    @Query("select * from movie_table")
    fun getAllMovies(): LiveData<List<Movie>>

    @Query("select * from movie_table where genre_id ==:genreId")
    fun getGenreMovies(genreId: Int): LiveData<List<Movie>>

}