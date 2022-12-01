package id.bazrira.dicoding.intermediate.abstraction.base

import android.app.Application
import android.content.Context
import id.bazrira.dicoding.intermediate.di.component.AppComponent
import id.bazrira.dicoding.intermediate.di.component.DaggerAppComponent
import id.bazrira.dicoding.intermediate.di.component.DaggerRepoComponent
import id.bazrira.dicoding.intermediate.di.component.RepoComponent
import id.bazrira.dicoding.intermediate.di.module.AppModule
import id.bazrira.dicoding.intermediate.di.module.DataModule
import id.bazrira.dicoding.intermediate.di.module.NetworkModule
import id.bazrira.dicoding.intermediate.di.module.repository.AuthRepositoryModule

class MyApplication: Application() {

  private lateinit var instance: MyApplication

  fun get(): MyApplication {
    return instance
  }

  override fun onCreate() {
    super.onCreate()
    instance = this
  }

  fun getRepoComponent(context: Context): RepoComponent {
    return DaggerRepoComponent.builder()
      .appComponent(getAppComponent(context))
      .authRepositoryModule(AuthRepositoryModule())
      .build()
  }

  private fun getAppComponent(context: Context): AppComponent {
    return DaggerAppComponent.builder()
      .appModule(AppModule(context))
      .networkModule(NetworkModule())
      .dataModule(DataModule(context))
      .build()
  }
}