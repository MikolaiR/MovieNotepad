package com.example.mvvmroomvmodelldatadbinding.model

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface GenreDao {

    @Insert
    fun insert(genre: Genre)

    @Update
    fun update(genre: Genre)

    @Delete
    fun delete(genre: Genre)

    @Query("select * from genre_table")
    fun getAllGenres():LiveData<List<Genre>>

}