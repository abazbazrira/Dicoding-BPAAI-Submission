package id.bazrira.dicoding.intermediate.di.component

import android.content.Context
import dagger.Component
import id.bazrira.dicoding.intermediate.abstraction.rx.SchedulerProvider
import id.bazrira.dicoding.intermediate.data.database.StoryDatabase
import id.bazrira.dicoding.intermediate.data.preferences.Preferences
import id.bazrira.dicoding.intermediate.di.module.AppModule
import id.bazrira.dicoding.intermediate.di.module.DataModule
import id.bazrira.dicoding.intermediate.di.module.NetworkModule
import id.bazrira.dicoding.intermediate.di.scope.Singleton
import id.bazrira.dicoding.intermediate.network.Services

@Component(
  modules = [
    AppModule::class,
    NetworkModule::class,
    DataModule::class,
  ]
)
@Singleton
interface AppComponent {
  fun context(): Context
  fun preferences(): Preferences
  fun schedulerProvider(): SchedulerProvider
  fun services(): Services
  fun storyDatabase(): StoryDatabase
}