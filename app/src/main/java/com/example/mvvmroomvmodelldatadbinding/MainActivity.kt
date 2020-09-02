package com.example.mvvmroomvmodelldatadbinding

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmroomvmodelldatadbinding.databinding.ActivityMainBinding
import com.example.mvvmroomvmodelldatadbinding.model.Genre
import com.example.mvvmroomvmodelldatadbinding.model.Movie
import com.example.mvvmroomvmodelldatadbinding.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val mainActivityViewModel by viewModels<MainActivityViewModel>()
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var mainActivityClickHandlers: MainActivityClickHandlers
    private lateinit var selectedGenre: Genre
    private lateinit var genreList: List<Genre>
    private lateinit var movieList: List<Movie>
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieAdapter
    private var selectedMovieId: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        /* findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
             Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                 .setAction("Action", null).show()
         }*/
        activityMainBinding =
            DataBindingUtil.setContentView(this@MainActivity, R.layout.activity_main)

        mainActivityClickHandlers = MainActivityClickHandlers()
        activityMainBinding.clickHandlers = mainActivityClickHandlers

        mainActivityViewModel.getGenres().observe(this, Observer {
            genreList = it
            for (genre in it) {
                Log.i("TAGtest", "genre: ${genre.genreName}")
            }
            showInSpinner()
        })
    }

    private fun showInSpinner() {
        val genreArrayAdapter = ArrayAdapter<Genre>(this, R.layout.spiner_item, genreList)
        genreArrayAdapter.setDropDownViewResource(R.layout.spiner_item)
        activityMainBinding.spinnerAdapter = genreArrayAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    inner class MainActivityClickHandlers {

        fun onFabClicked(view: View) {
            // Toast.makeText(this@MainActivity, "fab click", Toast.LENGTH_SHORT).show()
            val intent = Intent(this@MainActivity, AddEditActivity::class.java)
            startActivityForResult(intent, ADD_MOVIE_REQUEST_CODE)
        }

        fun onSelectedItem(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            selectedGenre = parent.getItemAtPosition(position) as Genre
            loadGenreMoviesInList(selectedGenre.id!!)
        }

    }

    private fun loadGenreMoviesInList(genreId: Int) {
        mainActivityViewModel.getGenreMovies(genreId).observe(this, Observer {

            movieList = it
            loadRecyclerView()
        })
    }

    private fun loadRecyclerView() {
        recyclerView = activityMainBinding.secondaryLayout.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        movieAdapter = MovieAdapter()
        movieAdapter.movieList = movieList
        recyclerView.adapter = movieAdapter

        movieAdapter.setOnItemClickListener(object : MovieAdapter.OnItemClickListener {
            override fun onItemClick(movie: Movie) {
                val intent = Intent(this@MainActivity, AddEditActivity::class.java)
                selectedMovieId = movie.movieId
                intent.putExtra(AddEditActivity.MOVE_ID, selectedMovieId)
                intent.putExtra(AddEditActivity.MOVE_NAME, movie.movieName)
                intent.putExtra(AddEditActivity.MOVE_DESCRIPTION, movie.movieDescription)
                startActivityForResult(intent, EDIT_MOVIE_REQUEST_CODE)
            }
        })

        ItemTouchHelper(object :ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val movieToDelete = movieList[viewHolder.adapterPosition]
                mainActivityViewModel.deleteMovie(movieToDelete)
            }
        }).attachToRecyclerView(recyclerView)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val selectedGenreId = selectedGenre.id

        if (requestCode == ADD_MOVIE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val movie = Movie()
            movie.genreId = selectedGenreId
            movie.movieName = data?.getStringExtra(AddEditActivity.MOVE_NAME)
            movie.movieDescription = data?.getStringExtra(AddEditActivity.MOVE_DESCRIPTION)

            mainActivityViewModel.addNewMovie(movie)

        } else if (requestCode == EDIT_MOVIE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val movie = Movie()

            movie.movieId = selectedMovieId
            movie.movieName = data?.getStringExtra(AddEditActivity.MOVE_NAME)
            movie.movieDescription = data?.getStringExtra(AddEditActivity.MOVE_DESCRIPTION)
            movie.genreId = selectedGenreId

            mainActivityViewModel.updateMovie(movie)
        }
    }

    companion object {
        const val ADD_MOVIE_REQUEST_CODE = 111
        const val EDIT_MOVIE_REQUEST_CODE = 222
    }
}