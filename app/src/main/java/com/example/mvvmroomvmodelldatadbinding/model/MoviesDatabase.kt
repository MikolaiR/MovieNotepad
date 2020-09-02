package com.example.mvvmroomvmodelldatadbinding.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(entities = [Genre::class, Movie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun getGenreDao(): GenreDao

    abstract fun getMovieDao(): MovieDao

    object InstanceDatabase {

        class Callback : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                if (instance != null) {
                    initialData(instance!!)
                }
            }
        }

        private var instance: MoviesDatabase? = null

        fun getInstance(context: Context): MoviesDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoviesDatabase::class.java,
                    "movieDB"
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(Callback())
                    .build()
            }
            return instance as MoviesDatabase
        }
    }
}

fun initialData(database: MoviesDatabase) {
    val genreDao = database.getGenreDao()
    val movieDao = database.getMovieDao()

    GlobalScope.launch(Dispatchers.IO) {
        val comedyGenre = Genre()
        comedyGenre.genreName = "Comedy"

        val romanceGenre = Genre()
        romanceGenre.genreName = "Romance"

        val dramaGenre = Genre()
        dramaGenre.genreName = "Drama"

        genreDao.insert(comedyGenre)
        genreDao.insert(romanceGenre)
        genreDao.insert(dramaGenre)

        val movie1 =
            Movie()
        movie1.movieName = "Bad Boys for Life"
        movie1.movieDescription =
            "The Bad Boys Mike Lowrey and Marcus Burnett are back together for one last ride in the highly anticipated Bad Boys for Life."
        movie1.genreId = 1

        val movie2 =
            Movie()
        movie2.movieName = "Parasite"
        movie2.movieDescription =
            "All unemployed, Ki-taek and his family take peculiar interest in the wealthy and glamorous Parks, as they ingratiate themselves into their lives and get entangled in an unexpected incident."
        movie2.genreId = 1

        val movie3 =
            Movie()
        movie3.movieName = " Once Upon a Time... in Hollywood"
        movie3.movieDescription =
            "A faded television actor and his stunt double strive to achieve fame and success in the film industry during the final years of Hollywood's Golden Age in 1969 Los Angeles."
        movie3.genreId = 1

        val movie4 =
            Movie()
        movie4.movieName = "You"
        movie4.movieDescription =
            "A dangerously charming, intensely obsessive young man goes to extreme measures to insert himself into the lives of those he is transfixed by."
        movie4.genreId = 2

        val movie5 =
            Movie()
        movie5.movieName = "Little Women"
        movie5.movieDescription =
            "Jo March reflects back and forth on her life, telling the beloved story of the March sisters - four young women each determined to live life on their own terms."
        movie5.genreId = 2

        val movie6 =
            Movie()
        movie6.movieName = "Vikings"
        movie6.movieDescription =
            "Vikings transports us to the brutal and mysterious world of Ragnar Lothbrok, a Viking warrior and farmer who yearns to explore - and raid - the distant shores across the ocean."
        movie6.genreId = 2

        val movie7 =
            Movie()
        movie7.movieName = "1917"
        movie7.movieDescription =
            "Two young British soldiers during the First World War are given an impossible mission: deliver a message deep in enemy territory that will stop 1,600 men, and one of the soldiers' brothers, from walking straight into a deadly trap."
        movie7.genreId = 3

        val movie8 =
            Movie()
        movie8.movieName = "The Witcher"
        movie8.movieDescription =
            "Geralt of Rivia, a solitary monster hunter, struggles to find his place in a world where people often prove more wicked than beasts."
        movie8.genreId = 3

        val movie9 =
            Movie()
        movie9.movieName = "The Outsider"
        movie9.movieDescription =
            "Investigators are confounded over an unspeakable crime that's been committed."
        movie9.genreId = 3

        movieDao.insert(movie1)
        movieDao.insert(movie2)
        movieDao.insert(movie3)
        movieDao.insert(movie4)
        movieDao.insert(movie5)
        movieDao.insert(movie6)
        movieDao.insert(movie7)
        movieDao.insert(movie8)
        movieDao.insert(movie9)
    }
}
