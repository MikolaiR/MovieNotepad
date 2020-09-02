package com.example.mvvmroomvmodelldatadbinding

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.mvvmroomvmodelldatadbinding.databinding.ActivityAddEditBinding
import com.example.mvvmroomvmodelldatadbinding.model.Movie


class AddEditActivity : AppCompatActivity() {

    private val movie: Movie = Movie()
    private lateinit var activityAddEditBinding: ActivityAddEditBinding
    private val addEditActivityClickHandlers = AddEditActivityClickHandlers(this@AddEditActivity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit)
        activityAddEditBinding =
            DataBindingUtil.setContentView(this@AddEditActivity, R.layout.activity_add_edit)
        activityAddEditBinding.movie = movie
        activityAddEditBinding.clickHandlers = addEditActivityClickHandlers

        val intent = intent
        if (intent.hasExtra(MOVE_ID)) {
            title = "Edit movie"
            movie.movieName = intent.getStringExtra(MOVE_NAME)
            movie.movieDescription = intent.getStringExtra(MOVE_DESCRIPTION)
        } else title = "Add movie"
    }

    inner class AddEditActivityClickHandlers(context: Context) {

        fun onOkButtonClicked(view: View) {
            if (movie.movieName == null) {
                Toast.makeText(this@AddEditActivity, "Please input name", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent()
                intent.putExtra(MOVE_NAME, movie.movieName)
                intent.putExtra(MOVE_DESCRIPTION, movie.movieDescription)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }

    }

    companion object {
        const val MOVE_ID = "movieID"
        const val MOVE_NAME = "movieName"
        const val MOVE_DESCRIPTION = "movieDescription"
    }
}