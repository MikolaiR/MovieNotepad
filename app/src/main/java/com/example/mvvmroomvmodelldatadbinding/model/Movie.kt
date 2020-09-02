package com.example.mvvmroomvmodelldatadbinding.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.mvvmroomvmodelldatadbinding.BR

@Entity(tableName = "movie_table", foreignKeys = [ForeignKey(
    entity = Genre::class,
    parentColumns = ["id"],
    childColumns = ["genre_id"],
    onDelete = CASCADE
)])
class Movie : BaseObservable() {
    @PrimaryKey(autoGenerate = true)
    var movieId: Int? = null
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieId)
        }

    @ColumnInfo(name = "movie_name")
    var movieName: String? = null
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieName)
        }

    @ColumnInfo(name = "movie_description")
    var movieDescription: String? = null
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.movieDescription)
        }

    @ColumnInfo(name = "genre_id")
    var genreId: Int? = null
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.genreId)
        }
}