package id.bazrira.dicoding.intermediate.di.module.repository

import dagger.Module
import dagger.Provides
import id.bazrira.dicoding.intermediate.data.preferences.Preferences
import id.bazrira.dicoding.intermediate.data.repository.story.StoryRepository
import id.bazrira.dicoding.intermediate.data.repository.story.StoryRepositoryImpl
import id.bazrira.dicoding.intermediate.data.repository.story.source.remote.StoryRemoteDataSource
import id.bazrira.dicoding.intermediate.data.repository.story.source.remote.StoryRemoteDataSourceImpl
import id.bazrira.dicoding.intermediate.di.scope.RepositoryScope
import id.bazrira.dicoding.intermediate.network.Services

@Module
class StoryRepositoryModule {

  @Provides
  @RepositoryScope
  fun provideStoryRemoteDataSource(
    services: Services
  ): StoryRemoteDataSource {
    return StoryRemoteDataSourceImpl(services)
  }

  @Provides
  @RepositoryScope
  fun provideStoryRepository(
    remoteDataSource: StoryRemoteDataSource,
    preferences: Preferences,
  ): StoryRepository {
    return StoryRepositoryImpl(remoteDataSource, preferences)
  }
}