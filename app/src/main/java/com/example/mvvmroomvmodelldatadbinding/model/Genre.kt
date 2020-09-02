package com.example.mvvmroomvmodelldatadbinding.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mvvmroomvmodelldatadbinding.BR

@Entity(tableName = "genre_table")
class Genre : BaseObservable() {
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.id)
        }

    @ColumnInfo(name = "genre_name")
    var genreName: String? = null
        @Bindable
        get
        set(value) {
            field = value
            notifyPropertyChanged(BR.genreName)
        }

    override fun toString(): String {
        return this.genreName!!
    }
}
