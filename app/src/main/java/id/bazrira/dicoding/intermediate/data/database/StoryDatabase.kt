package id.bazrira.dicoding.intermediate.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class StoryDatabase : RoomDatabase() {

  companion object {
    @Volatile
    private var INSTANCE: StoryDatabase? = null

    fun getInstance(context: Context): StoryDatabase = INSTANCE ?: synchronized(this) {
      INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
    }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(context.applicationContext, StoryDatabase::class.java, "Story.db")
        .fallbackToDestructiveMigration()
        .build()
  }
}
