package id.bazrira.dicoding.intermediate.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import id.bazrira.dicoding.intermediate.abstraction.rx.AppSchedulerProvider
import id.bazrira.dicoding.intermediate.abstraction.rx.SchedulerProvider
import id.bazrira.dicoding.intermediate.data.preferences.Preferences
import id.bazrira.dicoding.intermediate.data.preferences.PreferencesImpl
import id.bazrira.dicoding.intermediate.di.scope.Singleton

@Module
class AppModule(private val context: Context) {

  @Provides
  @Singleton
  fun provideContext(): Context = context

  @Provides
  @Singleton
  fun providePreferences(context: Context): Preferences = PreferencesImpl.getInstance(context)

  @Provides
  @Singleton
  fun provideAppSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}