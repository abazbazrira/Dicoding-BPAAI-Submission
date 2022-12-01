package id.bazrira.dicoding.intermediate.di.module.features.story

import dagger.Module
import dagger.Provides
import id.bazrira.dicoding.intermediate.data.repository.story.StoryRepository
import id.bazrira.dicoding.intermediate.data.usecases.story.StoryUseCase
import id.bazrira.dicoding.intermediate.data.usecases.story.StoryUseCaseImpl
import id.bazrira.dicoding.intermediate.di.scope.FeatureScope

@Module
class StoryModule {

  @Provides
  @FeatureScope
  fun provideStoryUseCase(
    repository: StoryRepository,
  ): StoryUseCase {
    return StoryUseCaseImpl(repository)
  }
}