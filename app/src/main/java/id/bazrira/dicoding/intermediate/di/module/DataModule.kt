package id.bazrira.dicoding.intermediate.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import id.bazrira.dicoding.intermediate.data.database.StoryDatabase
import id.bazrira.dicoding.intermediate.di.scope.Singleton

@Module
class DataModule(
  private val context: Context,
) {

  @Provides
  @Singleton
  fun provideStoryDatabase(): StoryDatabase = StoryDatabase.getInstance(context)
}