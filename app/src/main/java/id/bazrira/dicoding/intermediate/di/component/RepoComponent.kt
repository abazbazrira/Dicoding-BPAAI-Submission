package id.bazrira.dicoding.intermediate.di.component

import android.content.Context
import dagger.Component
import id.bazrira.dicoding.intermediate.abstraction.rx.SchedulerProvider
import id.bazrira.dicoding.intermediate.data.database.StoryDatabase
import id.bazrira.dicoding.intermediate.data.preferences.Preferences
import id.bazrira.dicoding.intermediate.data.repository.authentication.AuthRepository
import id.bazrira.dicoding.intermediate.data.repository.story.StoryRepository
import id.bazrira.dicoding.intermediate.di.module.repository.AuthRepositoryModule
import id.bazrira.dicoding.intermediate.di.module.repository.StoryRepositoryModule
import id.bazrira.dicoding.intermediate.di.scope.RepositoryScope
import id.bazrira.dicoding.intermediate.network.Services

@Component(
  dependencies = [AppComponent::class],
  modules = [
    AuthRepositoryModule::class,
    StoryRepositoryModule::class,
  ]
)
@RepositoryScope
interface RepoComponent {
  fun context(): Context
  fun preferences(): Preferences
  fun schedulerProvider(): SchedulerProvider
  fun services(): Services
//  fun storyDatabase(): StoryDatabase

  fun authRepository(): AuthRepository
  fun storyRepository(): StoryRepository
}