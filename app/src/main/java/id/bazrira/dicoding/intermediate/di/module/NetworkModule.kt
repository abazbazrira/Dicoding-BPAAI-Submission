package id.bazrira.dicoding.intermediate.di.module

import dagger.Module
import dagger.Provides
import id.bazrira.dicoding.intermediate.di.scope.Singleton
import id.bazrira.dicoding.intermediate.network.Network
import id.bazrira.dicoding.intermediate.network.Services
import retrofit2.create

@Module
class NetworkModule {

  @Provides
  @Singleton
  fun provideApiServices(): Services = Network.builder().create()
}